package kaptainwutax.nexus.path;

public abstract class PathAlgo {

    public abstract double getPathCost(double basePathCost);

    public abstract double getHeuristic(double baseHeuristic);

    public static PathAlgo A_STAR = new PathAlgo() {
        @Override
        public double getPathCost(double basePathCost) {
            return basePathCost;
        }

        @Override
        public double getHeuristic(double baseHeuristic) {
            return baseHeuristic;
        }
    };

    public static PathAlgo DIJKSTRA = new PathAlgo() {
        @Override
        public double getPathCost(double basePathCost) {
            return basePathCost;
        }

        @Override
        public double getHeuristic(double baseHeuristic) {
            return 0;
        }
    };

    public static PathAlgo GREEDY = new PathAlgo() {
        @Override
        public double getPathCost(double basePathCost) {
            return 0;
        }

        @Override
        public double getHeuristic(double baseHeuristic) {
            return baseHeuristic;
        }
    };

}
