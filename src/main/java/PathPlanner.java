import java.util.LinkedList;
import java.util.Queue;

public class PathPlanner {

    static class Node {
        int x, y;
        Node parent;

        Node(int x, int y, Node parent) {
            this.x = x;
            this.y = y;
            this.parent = parent;
        }
    }

    static Map planPath(Map map, Pose initialPose, Pose targetPose, int resizeWidth) {
        boolean[][] originalMap = map.getBooleanArray();
        int height = originalMap.length;
        int width = originalMap[0].length;

        // Find the closest path tile to the initial pose
        Node startNode = findClosestPathTile(originalMap, initialPose.getX(), initialPose.getY());
        // Find the closest path tile to the target pose
        Node endNode = findClosestPathTile(originalMap, targetPose.getX(), targetPose.getY());

        if (startNode == null || endNode == null) {
            // If either start or end position is not on a valid path, return an empty map
            return new Map(new boolean[height][width]);
        }

        boolean[][] visited = new boolean[height][width];
        boolean[][] pathMap = new boolean[height][width];

        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(startNode.x, startNode.y, null));
        visited[startNode.y][startNode.x] = true;

        Node endPathNode = null;

        // Directions for moving up, down, left, right
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        // Perform BFS to find the shortest path
        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // If we reached the target
            if (current.x == endNode.x && current.y == endNode.y) {
                endPathNode = current;
                break;
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                // Check if the new position is within bounds and not visited
                if (newX >= 0 && newX < width && newY >= 0 && newY < height && !visited[newY][newX] && originalMap[newY][newX]) {
                    queue.add(new Node(newX, newY, current));
                    visited[newY][newX] = true;
                }
            }
        }

        // If we found a path, trace it back
        if (endPathNode != null) {
            Node current = endPathNode;
            while (current != null) {
                pathMap[current.y][current.x] = true;
                current = current.parent;
            }

            // Widen the path according to the scale factor
            boolean[][] widenedPathMap = widenPath(pathMap, originalMap, resizeWidth);
            return new Map(widenedPathMap);
        }

        // If no path found, return an empty map
        return new Map(new boolean[height][width]);
    }

    private static Node findClosestPathTile(boolean[][] map, int x, int y) {
        int height = map.length;
        int width = map[0].length;
        boolean[][] visited = new boolean[height][width];
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(x, y, null));
        visited[y][x] = true;

        // Directions for moving up, down, left, right
        int[] dx = {0, 0, -1, 1};
        int[] dy = {-1, 1, 0, 0};

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (map[current.y][current.x]) {
                return current;
            }

            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];

                // Check if the new position is within bounds and not visited
                if (newX >= 0 && newX < width && newY >= 0 && newY < height && !visited[newY][newX]) {
                    queue.add(new Node(newX, newY, current));
                    visited[newY][newX] = true;
                }
            }
        }
        return null; // No path tile found
    }

    private static boolean[][] widenPath(boolean[][] pathMap, boolean[][] originalMap, int scaleFactor) {
        int height = pathMap.length;
        int width = pathMap[0].length;
        boolean[][] widenedPathMap = new boolean[height][width];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (pathMap[y][x]) {
                    // Apply the scaling factor to widen the path
                    for (int dy = -scaleFactor; dy <= scaleFactor; dy++) {
                        for (int dx = -scaleFactor; dx <= scaleFactor; dx++) {
                            int newY = y + dy;
                            int newX = x + dx;
                            if (newX >= 0 && newX < width && newY >= 0 && newY < height && originalMap[newY][newX]) {
                                widenedPathMap[newY][newX] = true;
                            }
                        }
                    }
                }
            }
        }

        return widenedPathMap;
    }
}