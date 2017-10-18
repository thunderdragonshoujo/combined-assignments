package com.cooksys.ftd.assignments.collections;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.cooksys.ftd.assignments.collections.hierarchy.Hierarchy;
import com.cooksys.ftd.assignments.collections.model.Capitalist;
import com.cooksys.ftd.assignments.collections.model.FatCat;
import com.cooksys.ftd.assignments.collections.model.WageSlave;

public class MegaCorp implements Hierarchy<Capitalist, FatCat> {
	private Set<Capitalist> hierarchy;
	public MegaCorp()
	{
		hierarchy = new TreeSet<Capitalist>();
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
    	Set<Capitalist> cap = new TreeSet<>();
    	cap.addAll(hierarchy);
    	return cap;
    }

    /**
     * @return all parent elements in the hierarchy,
     * or an empty set if no parents have been added to the hierarchy
     */
    @Override
    public Set<FatCat> getParents() {
    	Set<FatCat> fc = new TreeSet<>();
    	// Add all the fat cats to the returnSet
    	hierarchy.
    		stream().
    		filter(c -> c instanceof FatCat).
    		forEach(c -> fc.add((FatCat) c));
    	return fc;
    }

    /**
     * @param fatCat the parent whose children need to be returned
     * @return all elements in the hierarchy that have the given parent as a direct parent,
     * or an empty set if the parent is not present in the hierarchy or if there are no children
     * for the given parent
     */
    @Override
    public Set<Capitalist> getChildren(FatCat fatCat) {
    	Set<Capitalist> returnSet = new TreeSet<>();
    	// Make sure fatCat is in the hierarchy
    	if (has(fatCat))
    	{
    		for (Capitalist c : hierarchy)
    		{
    			if (c.hasParent())
    			{
    				// If the fatCat is equal to the parent of the capitalist in the hierarchy
    				// add it to the return set
					if (c.getParent().equals(fatCat))
					{
						returnSet.add(c);
					}
    			}
    		}
    	}
    	
    	return returnSet;
    }

    

    /**
     * @return a map in which the keys represent the parent elements in the hierarchy,
     * and the each value is a set of the direct children of the associate parent, or an
     * empty map if the hierarchy is empty.
     */
    @Override
    public Map<FatCat, Set<Capitalist>> getHierarchy() {
    	Map<FatCat, Set<Capitalist>> returnMap = new TreeMap<>();
        // Go through each element in the hierarchy as the parent to find each parents children
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
    	List<FatCat> returnSet = new ArrayList<FatCat>();
    	// Make sure capitalist isn't null and its in the list
    	if (capitalist == null || !has(capitalist.getParent()))
    	{
    		return returnSet;
    	}
    	// While there is a parent to move up to in the chain
    	while (capitalist.hasParent())
    	{
    		// Go through each capitalist in the hierarchy to find the next parent in the chain.
    		// When it is found, add the parent to the list and move on to that capitalist to find
    		// its next parent and repeat.
    		for (Capitalist c : hierarchy)
    		{
    			if (capitalist.hasParent())
    			{
					if (capitalist.getParent().equals(c))
					{
						returnSet.add(capitalist.getParent());
						capitalist = capitalist.getParent();
					}
    			}
    		}
    	}
    	
    	return returnSet;
    }

    }

