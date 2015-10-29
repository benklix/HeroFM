/*******************************************************************************
 * Copyright (C) 2014  Stefan Schroeder
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3.0 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this library.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

import jsprit.analysis.toolbox.GraphStreamViewer;
import jsprit.analysis.toolbox.GraphStreamViewer.Label;
import jsprit.analysis.toolbox.Plotter;
import jsprit.core.algorithm.VehicleRoutingAlgorithm;
import jsprit.core.algorithm.io.VehicleRoutingAlgorithms;
import jsprit.core.problem.Location;
import jsprit.core.problem.VehicleRoutingProblem;
import jsprit.core.problem.VehicleRoutingProblem.FleetSize;
import jsprit.core.problem.io.VrpXMLWriter;
import jsprit.core.problem.job.Shipment;
import jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import jsprit.core.problem.solution.route.VehicleRoute;
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleImpl.Builder;
import jsprit.core.problem.vehicle.VehicleType;
import jsprit.core.problem.vehicle.VehicleTypeImpl;
import jsprit.core.reporting.SolutionPrinter;
import jsprit.core.reporting.SolutionPrinter.Print;
import jsprit.core.util.Coordinate;
import jsprit.core.util.Solutions;
import jsprit.util.Examples;
import unit.Position;
import unit.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

import fab.Layout;
import fab.Request;


public class Example {
	
	public static void main(String[] args) {
		/*
		 * some preparation - create output folder
		 */
		Examples.createOutputFolder();
		
		/*
		 * build the blank VRP
		 */
		VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
		vrpBuilder.setFleetSize(FleetSize.FINITE);

		/*
		 * get a vehicle type-builder and build a type with the typeId "heroFab" and a capacity of 4
		 */
		int vehicleCap = 4;
		VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("heroFAB").addCapacityDimension(0, vehicleCap);
		vehicleTypeBuilder.setCostPerDistance(1.0).setCostPerTime(1.0);
		VehicleType vehicleType = vehicleTypeBuilder.build();
		
		/*
		 * build the current FAB layout
		 */
		Layout fabLayout = new Layout();
		
		/*
		 * TODO build a job-list
		 * this job-list generates the Shipments
		 */
		ArrayList<Request> requestList = new ArrayList<Request>();
		requestList.add(new Request(fabLayout.getToolAt(2), fabLayout.getToolAt(0), 1));
		requestList.add(new Request(fabLayout.getToolAt(6), fabLayout.getToolAt(0), 1));
		requestList.add(new Request(fabLayout.getToolAt(8), fabLayout.getToolAt(7), 1));
		requestList.add(new Request(fabLayout.getToolAt(10), fabLayout.getToolAt(13), 1));
		requestList.add(new Request(fabLayout.getToolAt(12), fabLayout.getToolAt(4), 1));
		
		/*
		 * letzte Zielorte der Heros > ABSPEICHERN!
		 */
		ArrayList<Position> posHeroList = new ArrayList<Position>();
		posHeroList.add(new Position(fabLayout.getToolCoordX(2), fabLayout.getToolCoordY(2)));
		posHeroList.add(new Position(fabLayout.getToolCoordX(8), fabLayout.getToolCoordY(8)));
		posHeroList.add(new Position(fabLayout.getToolCoordX(6), fabLayout.getToolCoordY(6)));


		/*
		 * build m vehicles and their start-locations
		 * the start location is the destination of the previous request
		 * they need to return to depot
		 */
				
		int nuOfVehicles = 3; //den Wert von irgendwo einlesen...
		int m = nuOfVehicles;
		
		for(int i=0; i<m; i++) {
	        String vehicleId = "hero-" + (i+1);
	        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance(vehicleId);
	        // the start location is the destination of the previous request -> abspeichern und hier übergeben!!!
	        vehicleBuilder.setStartLocation(Location.newInstance(posHeroList.get(i).getX(), posHeroList.get(i).getY()));
	        vehicleBuilder.setReturnToDepot(false);
	        vehicleBuilder.setType(vehicleType);
	        VehicleImpl vehicle = vehicleBuilder.build();
	        vrpBuilder.addVehicle(vehicle);
	    }
		
		/*
		 * build shipments at the required locations, each with a capacity-demand of 1.
		 *
		 */
		for(int i=0; i<requestList.size(); i++) {
	        String shipmentId = (i+1)+"-"+requestList.get(i).getSource().getName()+">"+requestList.get(i).getDestination().getName();
	        // Shipment kann mit Service-Zeiten u.A. beaufshlagt werden
	        Shipment shipment = Shipment.Builder.newInstance(shipmentId).addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(requestList.get(i).getSource().getPos().getX(), requestList.get(i).getSource().getPos().getY()))).setDeliveryLocation(loc(Coordinate.newInstance(requestList.get(i).getDestination().getPos().getX(), requestList.get(i).getDestination().getPos().getY()))).build();
	        vrpBuilder.addJob(shipment);
	    }
		
		/*
		 * initialize the VRP
		 */
		VehicleRoutingProblem problem = vrpBuilder.build();
		
		/*
		 * get the algorithm out-of-the-box. 
		 */
		VehicleRoutingAlgorithm algorithm = VehicleRoutingAlgorithms.readAndCreateAlgorithm(problem, "input/algorithmConfig.xml");
		algorithm.setMaxIterations(50);
		/*
		 * and search a solution
		 */
		Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
		
		/*
		 * get the best 
		 */
		VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
		
		/*
		 * Ausgabe der einzelnen Aufträge
		 * Koordinaten und Aktion kann als Befahl an Hero übergeben werden
		 */		
		for(Iterator<VehicleRoute> iterator=bestSolution.getRoutes().iterator(); iterator.hasNext(); ) {
			VehicleRoute vehRoute = iterator.next();
			for(int i=0; i<vehRoute.getTourActivities().getActivities().size(); i++) {
				System.out.print(vehRoute.getVehicle().getId());
				System.out.print(": "+vehRoute.getTourActivities().getActivities().get(i).getLocation().getCoordinate().getX());
				System.out.print(", "+vehRoute.getTourActivities().getActivities().get(i).getLocation().getCoordinate().getY());
				System.out.println(" > "+vehRoute.getTourActivities().getActivities().get(i).getName());
			}
		}
		
		/*
		 * Pseudo-Programm zur Steuerung der heros
		 */
		for(Iterator<VehicleRoute> iterator=bestSolution.getRoutes().iterator(); iterator.hasNext(); ) {
			VehicleRoute vehRoute = iterator.next();
			for(int i=0; i<vehRoute.getTourActivities().getActivities().size(); i++) {
				switch(vehRoute.getVehicle().getId()) {
					case "hero-1":
//						vehicle1.moveTo(vehRoute.getTourActivities().getActivities().get(i).getLocation().getCoordinate());
//						vehicle1.doAction(vehRoute.getTourActivities().getActivities().get(i));
						break;
					case "hero-2":
						break;
					case "hero-3":
						break;
					default:
						System.out.println("You should not get here...");
						break;
				}
			}
		}
		
				
		/*
		 * write out problem and solution to xml-file
		 */
		new VrpXMLWriter(problem, solutions).write("output/shipment-problem-with-solution.xml");
		
		/*
		 * print nRoutes and totalCosts of bestSolution
		 */
		SolutionPrinter.print(problem, bestSolution, Print.VERBOSE);
		
		/*
		 * plot problem without solution
		 */
		Plotter problemPlotter = new Plotter(problem);
		problemPlotter.plotShipments(true);
		problemPlotter.plot("output/enRoutePickupAndDeliveryWithMultipleLocationsExample_problem.png", "en-route pickup and delivery problem");
		
		/*
		 * plot problem with solution
		 */
		Plotter solutionPlotter = new Plotter(problem,Arrays.asList(Solutions.bestOf(solutions).getRoutes().iterator().next()));
		solutionPlotter.plotShipments(true);
		solutionPlotter.plot("output/enRoutePickupAndDeliveryWithMultipleLocationsExample_solution.png", "en-route pickup and delivery solution");
		
//		new GraphStreamViewer(problem,Solutions.bestOf(solutions)).labelWith(Label.ACTIVITY).setRenderDelay(100).setRenderShipments(true).display();
		
	}

	private static Location loc(Coordinate coordinate){
		return Location.Builder.newInstance().setCoordinate(coordinate).build();
	}

}

