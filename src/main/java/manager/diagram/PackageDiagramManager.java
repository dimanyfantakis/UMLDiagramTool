package manager.diagram;

import model.diagram.*;
import model.tree.LeafNode;
import model.tree.PackageNode;
import model.tree.Relationship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PackageDiagramManager extends DiagramManager{

    private final GraphMLNode<PackageNode> graphMLPackageNode;
    private final GraphMLPackageEdge graphMLPackageEdge;

    public PackageDiagramManager(Map<String, PackageNode> packageNodes) {
        super(packageNodes);
        graphMLPackageNode = new GraphMLPackageNode<>();
        graphMLPackageEdge = new GraphMLPackageEdge();
    }

    public void createDiagram(List<String> chosenPackagesNames) {
        graphMLPackageNode.populateGraphMLNodes(getChosenPackages(chosenPackagesNames));
        graphMLPackageEdge.setGraphMLNodes(graphMLPackageNode.getGraphMLNodes());
        graphMLPackageEdge.populateGraphMLEdges(getChosenPackages(chosenPackagesNames));
    }

    private List<PackageNode> getChosenPackages(List<String> chosenPackagesNames) {
        List<PackageNode> chosenPackages = new ArrayList<>();
        for (String chosenPackage: chosenPackagesNames) {
            if (packages.get(chosenPackage).isValid()) {
                chosenPackages.add(packages.get(chosenPackage));
            }
        }
        return chosenPackages;
    }

    public void arrangeDiagram() {
        DiagramArrangement<PackageNode> diagramArrangement = new PackageDiagramArrangement<>();
        diagramArrangement.arrangeDiagram(graphMLPackageNode.getGraphMLNodes(), graphMLPackageEdge.getGraphMLEdges());
        nodesGeometry = diagramArrangement.getNodesGeometry();
    }

    public void exportDiagramToGraphML(String graphMLSavePath) {
        graphMLPackageNode.convertNodesToGraphML(nodesGeometry);
        graphMLPackageEdge.convertEdgesToGraphML();
        GraphMLExporter graphMLExporter = new GraphMLExporter();
        graphMLExporter.exportDiagramToGraphML(graphMLSavePath, graphMLPackageNode.getGraphMLBuffer(), graphMLPackageEdge.getGraphMLBuffer());
    }

    public Map<String, Map<String, String>> getGraph() {
        Map<String, Map<String, String>> graph = new HashMap<>();
        for (PackageNode packageNode: graphMLPackageNode.getGraphMLNodes().keySet()) {
            Map<String, String> edgesTemp = new HashMap<>();
            for (Relationship<PackageNode> relationship: graphMLPackageEdge.getGraphMLEdges().keySet()){
                if (relationship.getStartingNode().equals(packageNode)) {
                    edgesTemp.put(relationship.getEndingNode().getName(), relationship.getRelationshipType().name());
                }
            }
            graph.put(packageNode.getName(), edgesTemp);
        }
        return graph;
    }

}
