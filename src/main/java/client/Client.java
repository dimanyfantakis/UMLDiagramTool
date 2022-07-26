package client;

import controller.DiagramController;
import controller.DiagramControllerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Client {

	public static void main(String[] args) {

		DiagramControllerFactory diagramControllerFactory = new DiagramControllerFactory();

		/*
		DiagramController classDiagramController = diagramControllerFactory.getController("Class");
		classDiagramController.createTree(args[0]);
		classDiagramController.convertTreeToDiagram(Arrays.asList("LatexEditorView", "VersionsManager", "CreateCommand", "CommandFactory", "LatexEditorController", "CommandFactory"));
		classDiagramController.arrangeDiagram();
		classDiagramController.exportDiagramToGraphML(args[2]);
		*/

		DiagramController packageDiagramController = diagramControllerFactory.getController("Package");
		packageDiagramController.createTree(args[0]);
		packageDiagramController.convertTreeToDiagram(Arrays.asList("controller", "model", "view", "commands", "strategies"));
		packageDiagramController.arrangeDiagram();
		packageDiagramController.exportDiagramToGraphML(args[2]);
	}

}
