package kaptainwutax.nexus.utility.math;

public class Pos {

    public int x;
    public int y;
    public int z;

    public Pos() {
    }

    public Pos(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Pos offset(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Pos offset(Pos pos) {
        this.x += pos.x;
        this.y += pos.y;
        this.z += pos.z;
        return this;
    }

    public Pos west() {
        this.x--;
        return this;
    }

    public Pos east() {
        this.x++;
        return this;
    }

    public Pos down() {
        this.y--;
        return this;
    }

    public Pos up() {
        this.y++;
        return this;
    }

    public Pos north() {
        this.z--;
        return this;
    }

    public Pos south() {
        this.z++;
        return this;
    }

    @Override
    public int hashCode() {
        return 961 * this.z + 31 * this.y + this.x;
    }

    public Pos copy() {
        return new Pos(this.x, this.y, this.z);
    }

}
