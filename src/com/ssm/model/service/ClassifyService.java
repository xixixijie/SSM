package com.ssm.model.service;

import com.ssm.model.bean.Classify;
import com.ssm.model.dao.ClassifyDAO;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by xixi on 2018/7/19.
 */
@Service
public class ClassifyService {

    @Autowired
    private ClassifyDAO classifyDAO;

    public void deleteClassify(String[] ids) {
        System.out.println("-----删除分类service-----");
        classifyDAO.deleteClassify(ids);
    }

    public void addClassify(Classify classify) {
        System.out.println("-----添加分类service-----");
        classifyDAO.addClassify(classify);

    }

    public Classify getClassify(int classifyID) {
        System.out.println("-----获取分类service-----");
        return classifyDAO.getClassify(classifyID);

    }

    public void ModifyClassify(Classify classify) {
//        System.out.println(classify.getClassifyID());
//        System.out.println(classify.getClassName());
//        System.out.println(classify.getClassDis());
//        System.out.println(classify.getClassState());
        System.out.println("-----修改分类service-----");
        classifyDAO.ModifyClassify(classify);

    }

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
                if (java.lang.Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i], t3);
                    t4 += t2[0];
                } else {
                    t4 += java.lang.Character.toString(t1[i]);
                }
            }
            return t4;
        } catch (BadHanyuPinyinOutputFormatCombination e1) {
            e1.printStackTrace();
        }
        return t4;
    }


    public List<Classify> findClassify(String content) {
        System.out.println("-----查找分类Service-----");
        //System.out.println(content);
        return classifyDAO.findClassify(content);
    }

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
}
