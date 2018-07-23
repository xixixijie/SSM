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

    public void deleteClassify(String[] ids){
        System.out.println("-----删除分类service-----");
        classifyDAO.deleteClassify(ids);
    }

    public void addClassify(Classify classify){
        System.out.println("-----添加分类service-----");
        classifyDAO.addClassify(classify);

    }

    public Classify getClassify(int classifyID){
        System.out.println("-----获取分类service-----");
        return classifyDAO.getClassify(classifyID);

    }

    public void ModifyClassify(Classify classify){
        System.out.println("-----修改分类service-----");
        classifyDAO.ModifyClassify(classify);

    }

    public List<Classify> showClassify(int type){
        System.out.println("-----展示分类service-----");
        List<Classify> list=classifyDAO.showClassify();
        if(list==null||list.size()==0){
            System.out.println("-----展示分类失败-----");
        }else{
            System.out.println("-----分类的大小为"+list.size()+"-----");
        }
       if(type==1){
            Collections.sort(list, new Comparator<Object>() {
                @Override
                public int compare(Object o1, Object o2) {
                    int x=((Classify)o1).getClassifyID();
                    int y=((Classify)o2).getClassifyID();
                    return (x > y) ? -1 : ((x == y) ? 0 : 1);
                }


            });
       }else if(type==2){
            return sortByName(list);
       }
       return list;

    }


    //字典序排序
    private List<Classify> sortByName(List<Classify> list) {
        HashMap<String,Classify> map=new HashMap<>();

        for(Classify classify:list){
            map.put(getPinYin(classify.getClassName()),classify);
        }

        Collection<String> keyset= map.keySet();

        List<String> keyList=new ArrayList<>(keyset);
        Collections.sort(keyList);
        List<Classify> newList=new ArrayList<>();
        for(int i=0;i<keyList.size();i++){
            newList.add(map.get(keyList.get(i)));
        }

        return newList;

    }


    private String getPinYin(String source) {
        if (source!=null && !"".equals(source)) {
            return "";
        }
        char[] t1 = source.toCharArray();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);
        StringBuilder result = new StringBuilder();
        for (char aT1 : t1) {
            if (Character.toString(aT1).matches("[\\u4E00-\\u9FA5]")) {
                try {
                    result.append(PinyinHelper.toHanyuPinyinStringArray(aT1, format)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    System.out.println("转换出错");
                }
            } else {
                // 非汉字不进行转换，直接添加
                result.append(aT1);
            }
        }
        return result.toString();
    }


    public List<Classify> findClassify(String content) {
        System.out.println("-----系统展示Service-----");
        return classifyDAO.findClassify(content);
    }
}
