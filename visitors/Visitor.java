package visitors;

import elements.DirectoryNode;
import elements.FileNode;

public interface Visitor {
    void visit(FileNode file);
    void visit(DirectoryNode directory);
}
