package com.sd.lifeng.util;

import com.sd.lifeng.vo.auth.ResourceTreeVO;

import java.util.HashSet;
import java.util.Set;

/**
 * 树状结构生成
 */
public class ResourceTreeUtils {
    //判断是否根节点，可根据实际情况修改
    public static boolean isRootElement(ResourceTreeVO resourceTreeVO){
        return resourceTreeVO.getParentId() == 0;
    }

    public static boolean isRootElement(int parentId){
        return parentId == 0;
    }

    /**
     * 获取子节点
     * 递归获取子节点的子节点
     * @param pid 父节点
     * @param rootList
     * @return
     */
    public static Set<ResourceTreeVO> getChildNodes(int pid, Set<ResourceTreeVO> rootList){
        Set<ResourceTreeVO> childList = new HashSet<>();
        for(ResourceTreeVO node:rootList){
            if(node.getParentId() == pid){
                childList.add(node);
            }
        }

        //递归查找子节点的子节点并赋值
        for(ResourceTreeVO c:childList){
            c.setChildren(getChildNodes(c.getId(), rootList));
        }

        return childList;
    }


//    public static void main(String[] args) {
//        List<Nodes> nodesList = new ArrayList<>();
//        nodesList.add(new Nodes("1", "0", "1"));
//        nodesList.add(new Nodes("1.1", "1", "1.1"));
//        nodesList.add(new Nodes("1.2", "1", "1.2"));
//        nodesList.add(new Nodes("1.2.1", "1.2", "1.2.1"));
//        nodesList.add(new Nodes("1.2.1.1", "1.2.1", "1.2.1.1"));
//        nodesList.add(new Nodes("1.3", "1", "1.3"));
//        nodesList.add(new Nodes("1.4", "1", "1.4"));
//        nodesList.add(new Nodes("1.3.1", "1.3", "1.3.1"));
//        nodesList.add(new Nodes("1.4.1", "1.4", "1.4.1"));
//        nodesList.add(new Nodes("1.4.1.1", "1.4。1", "1.4.1.1"));
//
//        List<Nodes> rootlist = new ArrayList<>();
//        //查找所有根节点
//        for (Nodes n:nodesList) {
//            if (isRootElement(n)) {
//                rootlist.add(n);
//            }
//        }
//        //根据根节点查找所有子节点
//        for(Nodes nc:rootlist){
//            nc.setChildNodes(getChildNodes(nc.getId(), nodesList));
//        }
//        //输出
//        buildMenu(rootlist);
//        System.out.println(str);
//    }
//
//    /**
//     * 遍历菜单
//     * @param arr
//     * @return
//     */
//    public static String str = "";
//    public static String buildMenu(List<Nodes> arr){
//
//        for(int i=0;i<arr.size();i++){
//            str += "<li>";
//            str += "<a href='javascript:;'>" +
//                    "<cite>"+arr.get(i).getId()+"</cite>" +
//                    "<i class='iconfont nav_right'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+arr.get(i).getName()+"</i>" +
//                    "</a>";
//            //存在子菜单 递归
//            if(arr.get(i).getChildNodes() != null && arr.get(i).getChildNodes().size() > 0){
//                str += "<ul>";
//                buildMenu(arr.get(i).getChildNodes()); // 递归
//                str += "</ul>";
//            }
//            str += "</li>";
//        }
//        return str;
//    }

}
