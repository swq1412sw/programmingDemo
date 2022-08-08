package com.sie.usergl.util;

import com.sie.usergl.vo.SieMenu;

import java.util.*;
import java.util.stream.Collectors;

public class HelpUtil {
    public static void ssk(String state, String start, String end, String page, String pagetotal, Map<String, Object> findmap) {
        findmap.put("state", state);
        findmap.put("start", start);
        findmap.put("end", end);

        int pagenumber = 1;
        int pagetotalnum = 20;
        try {
            pagenumber = Integer.parseInt(page);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            pagetotalnum = Integer.parseInt(pagetotal);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        pagenumber = (pagenumber - 1) * pagetotalnum;
        findmap.put("page", pagenumber);
        findmap.put("total", pagetotalnum);
    }

    public static List<SieMenu> getmenus(List<SieMenu> menus) {
        List<SieMenu> collect = menus.stream().filter(menu -> -1L == menu.getPid()
        ).collect(Collectors.toList());

        for (SieMenu sieMenu : collect) {
            getchildlevelmenu(sieMenu, menus);
        }

        return collect;
    }

    public static void getchildlevelmenu(SieMenu menu, List<SieMenu> menus) {

        menu.setChildlist(
                menus.stream().filter(
                        menu1 ->menu.getId().equals(menu1.getPid())
                ).collect(Collectors.toList()));
        for (int i = 0; i < menu.getChildlist().size(); i++) {
            getchildlevelmenu(menu.getChildlist().get(i), menus);
        }
        if (menu.getChildlist() == null) {
            menu.setChildlist(new ArrayList<>());
        }
    }
    public static List<SieMenu> filterData(List<SieMenu>findList,List<SieMenu>allList){
        if (findList.size()==allList.size()){
            return findList;
        }
        List<Long> idSet=new ArrayList<>();
        for (SieMenu sieMenu : findList) {
            idSet.add(sieMenu.getId());
        }
        List<Long> collect = getLongs(findList, idSet);

        idSet= listIsEmpty(idSet,collect,allList);
        Set<Long> collect1 = new HashSet<>(idSet);

        return allList.stream().filter(menu -> collect1.contains(menu.getId())).collect(Collectors.toList());

    }

    private static List<Long> getLongs(List<SieMenu> findList, List<Long> idSet) {
        return findList.stream().filter(menu -> menu.getPid() != -1L && !idSet.contains(menu.getPid())).map(SieMenu::getPid).collect(Collectors.toList());

    }

    public static List<Long> listIsEmpty(List<Long>idSet, List<Long>collect, List<SieMenu>allMenus){
        if (collect.isEmpty()){
            return idSet;
        }
        idSet.addAll(collect);
        collect=filterDataId(allMenus,collect);
      return listIsEmpty(idSet,collect,allMenus);
    }

    public static List<Long> filterDataId(List<SieMenu>allList,List<Long>pids){
        List<Long> collect = pids.stream().filter(pid -> pid != -1L).collect(Collectors.toList());
        return allList.stream().filter(menu -> collect.contains(menu.getId())).map(SieMenu::getPid).collect(Collectors.toList());

    }

    public  static List<SieMenu> lists(List<SieMenu>alllist,SieMenu menu,List<Long>ids,SieMenu menu2,List<SieMenu>findlist){
        if (new Long(-1L).equals(menu.getPid())){
            return findlist;
        }
        List<SieMenu> collect = alllist.stream().filter(menu1 -> menu.getPid().equals(menu1.getId())).collect(Collectors.toList());
        SieMenu sieMenu = new SieMenu();
        if (!collect.isEmpty()){
             sieMenu= collect.get(0);

        }
        if (ids.contains(sieMenu.getId())){
            findlist.removeIf(menu3 ->menu3.getId().equals(menu2.getId()) );
            for (int i = 0; i < findlist.size(); i++) {
                if (findlist.get(i).getId().equals(sieMenu.getId())){
                    findlist.get(i).getChildlist().add(menu2);
                    return findlist;
                }
            }
        }
        return lists(alllist,sieMenu,ids,menu2,findlist);

    }
}
