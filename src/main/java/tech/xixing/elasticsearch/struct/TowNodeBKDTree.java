import java.util.*;

public class TowNodeBKDTree {
    private Point[] points; // 空间点数组
    private Node root; // BKD 树根节点

    // 构造函数
    public TowNodeBKDTree(Point[] points) {
        // 克隆空间点数组，防止修改原始数据
        this.points = points.clone();
        this.root = build(0, points.length - 1, 0);
    }

    // 构建 BKD 树
    private Node build(int start, int end, int depth) {
        // 如果区间为空，返回 null
        if (start > end) {
            return null;
        }
        // 取中位数索引
        int mid = start + (end - start) / 2;
        // 根据当前深度选择排序维度
        int axis = depth % points[0].getDimension();
        // 按照排序维度比较空间点并排序
        Arrays.sort(points, start, end + 1, Comparator.comparing(p -> p.get(axis)));
        // 递归构建左子树和右子树
        Node left = build(start, mid - 1, depth + 1);
        Node right = build(mid + 1, end, depth + 1);
        // 返回当前节点
        return new Node(points[mid], left, right);
    }

    // 搜索最近的 k 个空间点
    public PriorityQueue<Point> search(Point query, int k) {
        // 创建最近邻点堆，并初始化为包含 k 个空间点
        PriorityQueue<Point> nearestPoints = new PriorityQueue<Point>(k, new Comparator<Point>() {
            public int compare(Point p1, Point p2) {
                // 比较两个点与查询点之间的距离
                double dist1 = p1.distance(query);
                double dist2 = p2.distance(query);
                return Double.compare(dist2, dist1);
            }
        });
        // 执行搜索
        search(root, query, k, nearestPoints);
        // 返回结果
        return nearestPoints;
    }

    // 递归搜索 BKD 树
    private void search(Node node, Point query, int k, PriorityQueue<Point> nearestPoints) {
        // 如果节点为空，返回
        if (node == null) {
            return;
        }
        // 计算当前节点到查询点的距离
        double dist = node.point.distance(query);
        // 如果最近邻点堆还没有填满，或者当前节点到查询点的距离小于堆顶元素的距离，加入堆中
        if (nearestPoints.size() < k || dist < nearestPoints.peek().distance(query)) {
            nearestPoints.offer(node.point);
            // 如果堆中元素数量超过 k，弹出堆顶元素
            if (nearestPoints.size() > k) {
                nearestPoints.poll();
            }
        }
        // 计算当前节点的分割平面与查询点的距离
        double splitDist = query.get(node.depth % query.getDimension()) - node.point.get(node.depth % node.point.getDimension());
        // 递归搜索子树
        if (splitDist < 0) {
            search(node.left, query, k, nearestPoints);
            if (nearestPoints.size() < k || Math.abs(splitDist) < nearestPoints.peek().distance(query)) {
                search(node.right, query, k, nearestPoints);
            }
        } else {
            search(node.right, query, k, nearestPoints);
            if (nearestPoints.size() < k || Math.abs(splitDist) < nearestPoints.peek().distance(query)) {
                search(node.left, query, k, nearestPoints);
            }
        }
    }

    public List<Point> search(Point query, double radius) {
        List<Point> result = new ArrayList<>();
        search(root, query, radius, result);
        return result;
    }

    private void search(Node node, Point query, double radius, List<Point> result) {
        if (node == null) {
            return;
        }
        double dist = node.getPoint().distance(query);
        if (dist <= radius) {
            result.add(node.getPoint());
        }
        double splitDist = query.get(node.getDepth() % query.getDimension()) - node.getPoint().get(node.getDepth() % node.getPoint().getDimension());
        if (splitDist < 0) {
            search(node.getLeft(), query, radius, result);
            if (Math.abs(splitDist) <= radius) {
                search(node.getRight(), query, radius, result);
            }
        } else {
            search(node.getRight(), query, radius, result);
            if (Math.abs(splitDist) <= radius) {
                search(node.getLeft(), query, radius, result);
            }
        }
    }
    // BKD 树节点类
    private static class Node {
        private Point point; // 节点对应的空间点
        private Node left; // 左子树
        private Node right; // 右子树

        private int depth; // 深度

        public Node(Point point, Node left, Node right) {
            this(point, left, right, 0);
        }

        public Node(Point point, Node left, Node right, int depth) {
            this.point = point;
            this.left = left;
            this.right = right;
            this.depth = depth;
        }

        public Point getPoint() {
            return point;
        }

        public Node getLeft() {
            return left;
        }

        public Node getRight() {
            return right;
        }
        public int getDepth() {
            return depth;
        }

    }
    public static class Point {
        private double[] coords; // 坐标数组

        public Point(double... coords) {
            this.coords = coords.clone();
        }

        // 获取点的坐标维度
        public int getDimension() {
            return coords.length;
        }

        // 获取点在指定维度上的坐标值
        public double get(int dimension) {
            return coords[dimension];
        }

        // 计算点与另一个点之间的欧几里得距离
        public double distance(Point other) {
            if (coords.length != other.coords.length) {
                throw new IllegalArgumentException("Points have different dimensions");
            }
            double sum = 0.0;
            for (int i = 0; i < coords.length; i++) {
                double diff = coords[i] - other.coords[i];
                sum += diff * diff;
            }
            return Math.sqrt(sum);
        }
    }


    public static void main(String[] args) {
        // 创建一个包含 10 个随机点的数组
        Point[] points = new Point[10];
        Random random = new Random();
        for (int i = 0; i < points.length; i++) {
            double x = random.nextDouble() * 100.0;
            double y = random.nextDouble() * 100.0;
            points[i] = new Point(x, y);
        }
        points[9] = new Point(121.48053886, 32);
        // 创建一个 BKD 树对象
        TowNodeBKDTree tree = new TowNodeBKDTree(points);
        // 构造查询点
        Point query = new Point(121.48053886, 31.23592904);
        // 搜索距离查询点最近的 3 个点
        PriorityQueue<Point> nearestPoints = tree.search(query, 3);

        List<Point> search = tree.search(query, 2.0);
        // 输出搜索结果
        System.out.println("Query point: " + query);
        System.out.println("Nearest points:");
        while (!nearestPoints.isEmpty()) {
            Point nearestPoint = nearestPoints.poll();
            System.out.println(nearestPoint + ", distance=" + query.distance(nearestPoint));
        }

        System.out.println("============================");
        while (!search.isEmpty()) {
            Point nearestPoint = search.remove(0);
            System.out.println(nearestPoint + ", distance=" + query.distance(nearestPoint));
        }
    }


}