package elements;

import visitors.Visitor;

public class FileNode implements Element {
    private final String name;
    private final long size;

    public FileNode(String name, long size) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("File name cannot be empty");
        }
        if (size < 0) {
            throw new IllegalArgumentException("File size cannot be negative: " + size);
        }
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
