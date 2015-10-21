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
import jsprit.core.problem.vehicle.VehicleImpl;
import jsprit.core.problem.vehicle.VehicleImpl.Builder;
import jsprit.core.problem.vehicle.VehicleType;
import jsprit.core.problem.vehicle.VehicleTypeImpl;
import jsprit.core.reporting.SolutionPrinter;
import jsprit.core.util.Coordinate;
import jsprit.core.util.Solutions;
import jsprit.util.Examples;
import unit.Unit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import fab.Layout;
import fab.Request;


public class Example {
	
	public static void main(String[] args) {
		/*
		 * some preparation - create output folder
		 */
		Examples.createOutputFolder();
		
		/*
		 * get a vehicle type-builder and build a type with the typeId "vehicleType" and a capacity of 2
		 */
		int vehicleCap = 4;
		VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("heroFAB").addCapacityDimension(0, vehicleCap);
		vehicleTypeBuilder.setCostPerDistance(1.0).setCostPerTime(1.0);
		VehicleType vehicleType = vehicleTypeBuilder.build();
		
		/*
		 * generate the current FAB layout
		 */
		Layout fabLayout = new Layout();
		
		/*
		 * define two vehicles and their start-locations 
		 * 
		 * they need to return to depot
		 */
		Builder vehicleBuilder1 = VehicleImpl.Builder.newInstance("hero-01");
		vehicleBuilder1.setStartLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(3), fabLayout.getToolCoordY(3)))).setReturnToDepot(false);
		vehicleBuilder1.setType(vehicleType);
		VehicleImpl vehicle1 = vehicleBuilder1.build();
		
		Builder vehicleBuilder2 = VehicleImpl.Builder.newInstance("hero-02");
		vehicleBuilder2.setStartLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(9), fabLayout.getToolCoordY(9)))).setReturnToDepot(false);
		vehicleBuilder2.setType(vehicleType);
		VehicleImpl vehicle2 = vehicleBuilder2.build();
		
		/*
		 * build shipments at the required locations, each with a capacity-demand of 1.
		 *
		 */
		
		/*
		 * TODO build a job-list
		 * this job-list generates the Shipments
		 */
		ArrayList<Request> requestList = new ArrayList<Request>();
		requestList.add(new Request(fabLayout.getToolAt(2), fabLayout.getToolAt(0), 1));
		
		Shipment shipment1 = Shipment.Builder.newInstance(requestList.get(0).getSource().getName()+" to "+requestList.get(0).getDestination().getName()).addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(3), fabLayout.getToolCoordY(3)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(1), fabLayout.getToolCoordY(1)))).build();
		Shipment shipment2 = Shipment.Builder.newInstance("2").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(3), fabLayout.getToolCoordY(3)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(4), fabLayout.getToolCoordY(4)))).build();
		
		Shipment shipment3 = Shipment.Builder.newInstance("3").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(3), fabLayout.getToolCoordY(3)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(5), fabLayout.getToolCoordY(5)))).build();
		Shipment shipment4 = Shipment.Builder.newInstance("4").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(3), fabLayout.getToolCoordY(3)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(7), fabLayout.getToolCoordY(7)))).build();
		
		Shipment shipment5 = Shipment.Builder.newInstance("5").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(4), fabLayout.getToolCoordY(4)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(3), fabLayout.getToolCoordY(3)))).build();
		Shipment shipment6 = Shipment.Builder.newInstance("6").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(4), fabLayout.getToolCoordY(4)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(5), fabLayout.getToolCoordY(5)))).build();
		
		Shipment shipment7 = Shipment.Builder.newInstance("7").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(6), fabLayout.getToolCoordY(6)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(2), fabLayout.getToolCoordY(2)))).build();
		Shipment shipment8 = Shipment.Builder.newInstance("8").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(7), fabLayout.getToolCoordY(7)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(1), fabLayout.getToolCoordY(1)))).build();
		
		Shipment shipment9 = Shipment.Builder.newInstance("9").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(7), fabLayout.getToolCoordY(7)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(8), fabLayout.getToolCoordY(8)))).build();
		Shipment shipment10 = Shipment.Builder.newInstance("10").addSizeDimension(0, 1).setPickupLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(9), fabLayout.getToolCoordY(9)))).setDeliveryLocation(loc(Coordinate.newInstance(fabLayout.getToolCoordX(2), fabLayout.getToolCoordY(2)))).build();
		
				
		VehicleRoutingProblem.Builder vrpBuilder = VehicleRoutingProblem.Builder.newInstance();
		vrpBuilder.addVehicle(vehicle1).addVehicle(vehicle2);
		vrpBuilder.addJob(shipment1).addJob(shipment2).addJob(shipment3).addJob(shipment4);
		vrpBuilder.addJob(shipment5).addJob(shipment6).addJob(shipment7).addJob(shipment8);
		vrpBuilder.addJob(shipment9).addJob(shipment10);
		
		vrpBuilder.setFleetSize(FleetSize.FINITE);
		
		VehicleRoutingProblem problem = vrpBuilder.build();
		
		/*
		 * get the algorithm out-of-the-box. 
		 */
		VehicleRoutingAlgorithm algorithm = VehicleRoutingAlgorithms.readAndCreateAlgorithm(problem, "input/algorithmConfig.xml");
//		algorithm.setMaxIterations(30000);
		/*
		 * and search a solution
		 */
		Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
		
		/*
		 * get the best 
		 */
		VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);
		
		/*
		 * write out problem and solution to xml-file
		 */
		new VrpXMLWriter(problem, solutions).write("output/shipment-problem-with-solution.xml");
		
		/*
		 * print nRoutes and totalCosts of bestSolution
		 */
		SolutionPrinter.print(bestSolution);
		
		/*
		 * plot problem without solution
		 */
		Plotter problemPlotter = new Plotter(problem);
		problemPlotter.plotShipments(true);
		problemPlotter.plot("output/enRoutePickupAndDeliveryWithMultipleLocationsExample_problem.png", "en-route pickup and delivery");
		
		/*
		 * plot problem with solution
		 */
		Plotter solutionPlotter = new Plotter(problem,Arrays.asList(Solutions.bestOf(solutions).getRoutes().iterator().next()));
		solutionPlotter.plotShipments(true);
		solutionPlotter.plot("output/enRoutePickupAndDeliveryWithMultipleLocationsExample_solution.png", "en-route pickup and delivery");
		
		new GraphStreamViewer(problem,Solutions.bestOf(solutions)).labelWith(Label.ACTIVITY).setRenderDelay(100).setRenderShipments(true).display();
		
	}

	private static Location loc(Coordinate coordinate){
		return Location.Builder.newInstance().setCoordinate(coordinate).build();
	}

}

