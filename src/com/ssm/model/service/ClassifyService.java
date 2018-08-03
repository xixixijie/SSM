package com.ssm.model.service;

import com.ssm.model.bean.Classify;
import com.ssm.model.bean.Keyword;
import com.ssm.model.bean.keyLabel;
import com.ssm.model.bean.Point;
import com.ssm.model.dao.ClassifyDAO;
import com.ssm.model.dao.KeywordDAO;
import com.ssm.util.FileUtil;


import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vec.Learn;
import vec.Word2VEC;

import java.io.*;
import java.util.*;

/**
 * Created by xixi on 2018/7/19.
 */
@Service
public class ClassifyService {

    @Autowired
    private ClassifyDAO classifyDAO;

    @Autowired
    private KeywordDAO keywordDAO;

    /**
     * 删除分类
     *
     * @param ids
     */

    public void deleteClassify(String[] ids) {
        System.out.println("-----删除分类service-----");
        classifyDAO.deleteClassify(ids);
    }

    /**
     * 添加分类
     *
     * @param classify
     */

    public void addClassify(Classify classify) {
        System.out.println("-----添加分类service-----");
        classifyDAO.addClassify(classify);

    }

    /**
     * 获得分类
     *
     * @param classifyID
     * @return
     */

    public Classify getClassify(int classifyID) {
        System.out.println("-----获取分类service-----");
        return classifyDAO.getClassify(classifyID);

    }

    /**
     * 修改分类
     *
     * @param classify
     */

    public void ModifyClassify(Classify classify) {
//        System.out.println(classify.getClassifyID());
//        System.out.println(classify.getClassName());
//        System.out.println(classify.getClassDis());
//        System.out.println(classify.getClassState());
        System.out.println("-----修改分类service-----");
        classifyDAO.ModifyClassify(classify);

    }

    /**
     * 展示分类
     *
     * @return
     */

    public List<Classify> showClassify() {
        System.out.println("-----展示分类service-----");
        List<Classify> list = classifyDAO.showClassify();
        if (list == null || list.size() == 0) {
            System.out.println("-----展示分类失败-----");
        } else {
            System.out.println("-----分类的大小为" + list.size() + "-----");
        }
//
        return list;

    }


    /**
     * 字典序排序
     *
     * @param list
     * @return
     */
    //字典序排序
    private List<Classify> sortByName(List<Classify> list) {
        //System.out.println("字典序排序"+list.size());
        HashMap<String, Classify> map = new HashMap<>();

        for (Classify classify : list) {
            //System.out.println(classify.getClassName());
            //System.out.println(getPingYin(classify.getClassName()));
            map.put(getPingYin(classify.getClassName()), classify);
        }
        // System.out.println("map大小"+map.size());

        Collection<String> keyset = map.keySet();
        //System.out.println("keyset大小"+keyset.size());
        List<String> keyList = new ArrayList<>(keyset);
        //System.out.println("keyLIst大小"+keyList.size());
        Collections.sort(keyList);
        List<Classify> newList = new ArrayList<>();
        for (int i = 0; i < keyList.size(); i++) {
            //System.out.println(keyList.get(i));
            newList.add(map.get(keyList.get(i)));
        }

        return newList;

    }

    /**
     * 根据汉字获得拼音
     *
     * @param src
     * @return
     */

    private String getPingYin(String src) {
        char[] t1 = null;
        t1 = src.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断是否为汉字字符
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }

    /**
     * 查找分类
     *
     * @param content
     * @return
     */

    public List<Classify> findClassify(String content) {
        System.out.println("-----查找分类Service-----");
        //System.out.println(content);
        return classifyDAO.findClassify(content);
    }

    /**
     * 排序分类
     *
     * @param type
     * @param ids
     * @return
     */

    public List<Classify> sortClassify(int type, String[] ids) {
        System.out.println("-----排序分类service-----");
        List<Classify> list = classifyDAO.getClassifys(ids);
        if (type == 1) {
            Collections.sort(list, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    int x = ((Classify) o1).getClassifyID();
                    int y = ((Classify) o2).getClassifyID();
                    return (x < y) ? -1 : ((x == y) ? 0 : 1);
                }
            });
            return list;
        } else if (type == 2) {
            return sortByName(list);
        }

        //随机打乱list顺序
        List<Classify> list1 = new ArrayList<>();
        Set<Classify> set = new HashSet<>(list);

        for (Iterator iterator = set.iterator(); iterator.hasNext(); ) {
            list1.add((Classify) iterator.next());
        }
        return list1;


    }

    /**
     * 分词
     *
     * @param adjList
     * @param content
     * @param pipeline
     * @return
     */

//    public static String divide(List<String> adjList, String content, StanfordCoreNLP pipeline) {
////		String path = "./data/ace2004/RawTexts/chtb_165.eng";
////        String content = "质量很好，是纯棉的，物流很快，很喜欢，款式很好看。喜欢的亲就购买吧？";
//        // build pipeline
//
//        Annotation document = new Annotation(content);
//        pipeline.annotate(document);
//
//        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
//        String result = "";
//        for (CoreMap sentence : sentences) {
//            // traversing the words in the current sentence
//            // a CoreLabel is a CoreMap with additional token-specific methods
//            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
//                // this is the text of the token
//                String word = token.get(CoreAnnotations.TextAnnotation.class);
//                // this is the POS tag of the token
//                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
//                // this is the NER label of the token
//                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
//                //System.out.println(word + "\t" + pos + "\t" + ne);
//                result += word + " ";
//
//                if (pos.equals("VA"))
//                    adjList.add(word);
//            }
//
//
//        }
//        return result;
//    }


    /**
     * 更新向量模型
     */
//    public void updateModel() {
//        System.out.println("更新模型service");
//        //注册配置文件
//        StanfordCoreNLP pipeline = new StanfordCoreNLP("CoreNLP-chinese.properties");
//
//
//        //更新语料库
//        //读取所有评论句子
//        String[] strings = FileUtil.read();
//
//        //所有形容词
//        List<String> adjList = new ArrayList<>();
//        System.out.println("句子大小" + strings.length);
//        //更新xh.txt
//        for (String string : strings) {
//            if (string == null)
//                continue;
//            FileUtil.write(divide(adjList, string, pipeline), "xh.txt");
//        }
////        for(String str:adjList){
////            System.out.println(str);
////        }
////        System.out.println(adjList.size());
//        System.out.println("分词完成");
//        Set<String> adjSet = new HashSet<>(adjList);
////        for(String str:adjSet){
////            System.out.println(str);
////        }
////        System.out.println(adjSet.size());
//        //更新模型
//        Learn learn = new Learn();
//        long start = System.currentTimeMillis();
//        try {
//            learn.learnFile(new File("/Users/xixi/Desktop/SSM/library/xh.txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        System.out.println("use time " + (System.currentTimeMillis() - start));
//        learn.saveModel(new File("/Users/xixi/Desktop/SSM/library/javaVector"));
//
//
//        //更新关键词标签 以及 关键词库
//        Word2VEC vec = new Word2VEC();
//        try {
//            vec.loadJavaModel("/Users/xixi/Desktop/SSM/library/javaVector");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        HashMap<String, float[]> wordMap = vec.getWordMap();
//
//
//        for (String str : adjSet) {
//            System.out.println(str + " " + Arrays.toString(wordMap.get(str)));
//        }
//        //DBSCAN聚类
//        //点数组
//        List<Point> points = new ArrayList<>();
//        //填充点数组
//        for (String str : adjSet) {
//            Point point = new Point();
//            point.setName(str);
//            point.setVec(wordMap.get(str));
//            points.add(point);
//        }
//
//        System.out.println("点大小" + points.size());
//
//        //簇半径 到时候改为0.4
//        double r = 1;
//        //簇大小
//        int clusterSize = 2;
//        //簇数量
//        int clusterNum = DBSCAN(points, r, clusterSize);
//        System.out.println("簇大小" + clusterNum);
//
//        //计算中心词
//        getCenter(clusterNum,points);
//
//        //中心点作为 该簇的代表词
//        List<Point> centerPoint=new ArrayList<>();
//        for(Point p:points){
//            if(p.isCenter()){
//                centerPoint.add(p);
//            }
//        }
//
//
//        //keywordDAO.deleteAll();
//        //中心点存为 keyLabel
//
//        for(Point cp:centerPoint){
//            keyLabel label=new keyLabel();
//            label.setLabelName(cp.getName());
//            int num=0;
//            //获得该中心点的簇的所有点
//            System.out.println("中心点是"+cp.getName()+"簇类是"+cp.getCid());
//            List<Point> clu=getCluster(cp,points);
//            for(Point point:clu){
//                System.out.println("边缘点是"+point.getName()+"簇类是"+point.getCid());
//                for(String str:adjList){
//                    if(str.equals(point.getName())){
//                        num++;
//                    }
//                }
//            }
//            label.setLabelNum(num);
//            //label.setLabelID(keywordDAO.getMaxLabelID());
//            //keywordDAO.addKeyLabel(label);
//            //簇内的词存为关键词
//            for(Point point:clu){
//                Keyword keyword=new Keyword();
//                keyword.setLabelID(label.getLabelID());
//                keyword.setKeyName(point.getName());
//                int num1=0;
//                for(String str:adjList){
//                    if(str.equals(point.getName())){
//                        num1++;
//                    }
//                }
//                keyword.setKeyNum(num);
//
//                //keywordDAO.addKeyword(keyword);
//
//            }
//
//
//
//
//        }
//
//
//    }

    /**
     * 获得相同簇的点
     *
     * @param p
     * @param points
     * @return
     */

    private List<Point> getCluster(Point p, List<Point> points) {
        List<Point> temp = new ArrayList<>();
        for (Point point : points) {
            if (point.getCid() == p.getCid())
                temp.add(point);
        }
        return temp;
    }

    /**
     * 获得中心点
     *
     * @param clusterNum
     * @param points
     */
    private void getCenter(int clusterNum, List<Point> points) {
        for (int i = 1; i <= clusterNum; i++) {
            List<Point> cluster = new ArrayList<>();
            for (Point p : points) {
                if (p.getCid() == i) {
                    cluster.add(p);
                }
            }
            CalculateCenter(cluster);
        }
    }

    /**
     * 计算中心点
     *
     * @param cluster
     */
    private void CalculateCenter(List<Point> cluster) {

        double minDistance = 10000;
        int center = 0;
        int cluSize = cluster.size();
        for (int i = 0; i < cluSize; i++) {
            //平均距离
            double weightDitstance = 0;
            float[] vec1 = cluster.get(i).getVec();
            for (int j = 0; j < cluSize; j++) {
                double distance = 0;
                float[] vec2 = cluster.get(j).getVec();
                int size = vec1.length;
                //计算i到j的距离
                for (int k = 0; k < size; k++) {
                    //System.out.println(floats[j]-floats1[j]);
                    distance = distance + (vec1[j] - vec2[j]) * (vec1[j] - vec2[j]);
                }

                weightDitstance += Math.sqrt(distance);

            }

            if (weightDitstance < minDistance) {
                center = i;
                minDistance = weightDitstance;
            }

        }

        for (int i = 0; i < cluSize; i++) {
            if (i == center)
                cluster.get(i).setCenter(true);
            else
                cluster.get(i).setCenter(false);
        }

    }

    /**
     * DBSCAN 算法
     *
     * @param points
     * @param r
     * @param size
     * @return
     */
    private int DBSCAN(List<Point> points, double r, int size) {
        int clusterNum = 0;

        //
        boolean allVisited = false;

        while (!allVisited) {

            for (Point point : points) {
                System.out.println("点" + point.getName());
                //被访问就跳过
                if (point.isVisited()) {
                    continue;
                }
                allVisited = false;
                point.setVisited(true);
                List<Point> neighbors = getNeighbors(point, points, r);
                //判断是中心点还是边界点
                if (neighbors.size() < size) {
                    //边界邻居太少 不形成簇 设置簇id为-1
                    if (point.getCid() <= 0) {
                        point.setCid(-1);
                    }
                } else {
                    //形成新的簇
                    //新的点
                    if (point.getCid() <= 0) {
                        clusterNum++;
                        //合并成簇
                        expandCluster(point, neighbors, clusterNum, points);
                    } else {
                        //被扩展过的点
                        int cid = point.getCid();
                        expandCluster(point, neighbors, cid, points);
                    }
                }
            }

            allVisited = true;

        }


        return clusterNum;
}

    /**
     * 扩展合并簇
     *
     * @param point
     * @param neighbors
     * @param cid
     * @param points
     */

    public void expandCluster(Point point, List<Point> neighbors, int cid, List<Point> points) {
        //将该点簇id设置为cid
        point.setCid(cid);

        for (Point p : neighbors) {
            //已经是某个簇成员了
            if (!p.isVisited()) {
                p.setVisited(true);
                //获得p点的邻居 半径大小改为0.4
                List<Point> pNeighbors = getNeighbors(p, points, 1);

                //簇合并
                if (pNeighbors.size() > 2) {
                    for (Point np : pNeighbors) {
                        np.setCid(cid);
                    }
                }
            }
            //q不是任何簇的成员
            if (p.getCid() <= 0) {
                p.setCid(cid);
            }

        }
    }

    /**
     * 计算p点的所有邻居
     *
     * @param p
     * @param points
     * @param r
     * @return
     */
    //算p点的邻居
    public List<Point> getNeighbors(Point p, List<Point> points, double r) {
        List<Point> neighbors = new ArrayList<>();
        System.out.println("半径大小"+r);
        for (Point point : points) {
            float[] vec1 = p.getVec();
            float[] vec2 = point.getVec();
            int size = vec1.length;
            double distance = 0;
            for (int j = 0; j < size; j++) {
                //System.out.println(vec1[j]-vec2[j]);
                distance = distance + (vec1[j] - vec2[j]) * (vec1[j] - vec2[j]);
            }
            System.out.println(p.getName()+" "+point.getName()+"距离之间"+Math.sqrt(distance));
            //如果距离小于半径
            if (Math.sqrt(distance) < r)
                neighbors.add(point);
        }

        return neighbors;

    }

    /**
     * 计算词1和词2的相似度
     *
     * @param word1
     * @param word2
     * @return
     */


    public double getSimilarity(String word1, String word2) {
        Word2VEC vec = new Word2VEC();

        try {
            vec.loadJavaModel("/Users/xixi/Desktop/SSM/library/javaVector");
        } catch (IOException e) {
            e.printStackTrace();
        }

//		System.out.println(Arrays.toString(vec.getWordVector("漂亮")));

        return vec.distanceWith2Words(word1, word2);

    }
}
