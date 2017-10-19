package com.cooksys.ftd.assignments.collections;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	private Set<Capitalist> hierarchy;
	public MegaCorp()
	{
		hierarchy = new HashSet<Capitalist>();
	}

    /**
     * Adds a given element to the hierarchy.
     * <p>
     * If the given element is already present in the hierarchy,
     * do not add it and return false
     * <p>
     * If the given element has a parent and the parent is not part of the hierarchy,
     * add the parent and then add the given element
     * <p>
     * If the given element has no parent but is a Parent itself,
     * add it to the hierarchy
     * <p>
     * If the given element has no parent and is not a Parent itself,
     * do not add it and return false
     *
     * @param capitalist the element to add to the hierarchy
     * @return true if the element was added successfully, false otherwise
     */
    @Override
    public boolean add(Capitalist capitalist) {
    	if (capitalist == null || !capitalist.hasParent() && capitalist instanceof WageSlave)
        {
        	return false;
        }
    	else
    	{
    		add(capitalist.getParent());
    	}
    	return hierarchy.add(capitalist);
    }

    /**
     * @param capitalist the element to search for
     * @return true if the element has been added to the hierarchy, false otherwise
     */
    @Override
    public boolean has(Capitalist capitalist) {
        return hierarchy.contains(capitalist);
    }

    /**
     * @return all elements in the hierarchy,
     * or an empty set if no elements have been added to the hierarchy
     */
    @Override
    public Set<Capitalist> getElements() {
    	Set<Capitalist> returnSet = new HashSet<>();
    	returnSet.addAll(hierarchy);
    	return returnSet;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> returnSet = new HashSet<>();
    	hierarchy.
    		stream().
    		filter(c -> c instanceof FatCat).
    		forEach(c -> returnSet.add((FatCat) c));
    	return returnSet;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> returnSet = new HashSet<>();
    	if (has(fatCat))
    		for (Capitalist c : hierarchy)
    			if (c.hasParent())
					if (c.getParent().equals(fatCat))
						returnSet.add(c);
    	
    	return returnSet;
    }

    

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	Map<FatCat, Set<Capitalist>> returnMap = new HashMap<>();
                hierarchy.
        	stream().
        	filter(c -> c instanceof FatCat).
        	forEach(parent -> returnMap.put((FatCat) parent, getChildren((FatCat) parent)));   
        return returnMap;
    }

    /**
     * @param capitalist
     * @return the parent chain of the given element, starting with its direct parent,
     * then its parent's parent, etc, or an empty list if the given element has no parent
     * or if its parent is not in the hierarchy
     */
    @Override
    public List<FatCat> getParentChain(Capitalist capitalist) {
    	List<FatCat> defenseList = new ArrayList<FatCat>();
    	if (capitalist == null || !has(capitalist.getParent()))
    	{
    		return defenseList;
    	}
    	while (capitalist.hasParent())
    	{
    		for (Capitalist c : hierarchy)
    		{
    			if (capitalist.hasParent())
    			{
					if (capitalist.getParent().equals(c))
					{
						defenseList.add(capitalist.getParent());
						capitalist = capitalist.getParent();
					}
    			}
    		}
    	}
    	 
    	return defenseList;
    }

    }

