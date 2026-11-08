package visitors;

import elements.DirectoryNode;
import elements.FileNode;
import elements.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class SearchVisitor implements Visitor {
    private final String targetName;
    private final long minSize;
    private final Stack<String> pathStack = new Stack<>();
    private final List<String> matchingPaths = new ArrayList<>();

    public SearchVisitor(String targetName, long minSize) {
        if (minSize < 0) {
            throw new IllegalArgumentException("Minimum size threshold cannot be negative");
        }
        this.targetName = targetName;
        this.minSize = minSize;
    }

    public List<String> getMatchingPaths() {
        List<String> sortedPaths = new ArrayList<>(matchingPaths);
        Collections.sort(sortedPaths);
        return Collections.unmodifiableList(sortedPaths);
    }

    @Override
    public void visit(FileNode file) {
        boolean nameMatch = (targetName == null || targetName.isEmpty() || file.getName().contains(targetName));
        boolean sizeMatch = (file.getSize() >= minSize);

        if (nameMatch && sizeMatch) {
            String fullPath = String.join("/", pathStack) + "/" + file.getName();
            matchingPaths.add(fullPath);
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        pathStack.push(directory.getName());
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        pathStack.pop();
    }
}
