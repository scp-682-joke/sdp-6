package visitors;

import elements.DirectoryNode;
import elements.FileNode;
import elements.Element;

public class TotalSizeVisitor implements Visitor {
    private long totalSize = 0;

    public long getTotalSize() {
        return totalSize;
    }

    public void reset() {
        this.totalSize = 0;
    }

    @Override
    public void visit(FileNode file) {
        this.totalSize += file.getSize();
    }

    @Override
    public void visit(DirectoryNode directory) {
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
    }
}
