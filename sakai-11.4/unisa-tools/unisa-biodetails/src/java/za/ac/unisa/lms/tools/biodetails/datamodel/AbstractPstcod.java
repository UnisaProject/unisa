/*
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized
 * by MyEclipse Hibernate tool integration.
 *
 * Created Fri Sep 23 07:51:05 GMT+02:00 2005 by MyEclipse Hibernate Tool.
 */
package za.ac.unisa.lms.tools.biodetails.datamodel;

import java.io.Serializable;

/**
 * A class that represents a row in the PSTCOD table. 
 * You can customize the behavior of this class by editing the class, {@link Pstcod()}.
 * WARNING: DO NOT EDIT THIS FILE. This is a generated file that is synchronized * by MyEclipse Hibernate tool integration.
 */

public abstract class AbstractPstcod implements Serializable {

	private static final long serialVersionUID = 1204412892319538695L;

	/** The value of the simple code property. */
    private java.lang.Short code;

    /** The value of the simple suburb property. */
    private java.lang.String suburb;

    /** The value of the simple type property. */
    private java.lang.String type;

    /** The value of the simple town property. */
    private java.lang.String town;

    /** The value of the simple sortfield property. */
    private java.lang.String sortfield;

    /**
     * Simple constructor of AbstractPstcod instances.
     */
    public AbstractPstcod()
    {
    }

    /**
     * Return the value of the CODE column.
     * @return java.lang.Short
     */
    public java.lang.Short getCode()
    {
        return this.code;
    }

    /**
     * Set the value of the CODE column.
     * @param code
     */
    public void setCode(java.lang.Short code)
    {
        this.code = code;
    }

    /**
     * Return the value of the SUBURB column.
     * @return java.lang.String
     */
    public java.lang.String getSuburb()
    {
        return this.suburb;
    }

    /**
     * Set the value of the SUBURB column.
     * @param suburb
     */
    public void setSuburb(java.lang.String suburb)
    {
        this.suburb = suburb;
    }

    /**
     * Return the value of the TYPE column.
     * @return java.lang.String
     */
    public java.lang.String getType()
    {
        return this.type;
    }

    /**
     * Set the value of the TYPE column.
     * @param type
     */
    public void setType(java.lang.String type)
    {
        this.type = type;
    }

    /**
     * Return the value of the TOWN column.
     * @return java.lang.String
     */
    public java.lang.String getTown()
    {
        return this.town;
    }

    /**
     * Set the value of the TOWN column.
     * @param town
     */
    public void setTown(java.lang.String town)
    {
        this.town = town;
    }

    /**
     * Return the value of the SORTFIELD column.
     * @return java.lang.String
     */
    public java.lang.String getSortfield()
    {
        return this.sortfield;
    }

    /**
     * Set the value of the SORTFIELD column.
     * @param sortfield
     */
    public void setSortfield(java.lang.String sortfield)
    {
        this.sortfield = sortfield;
    }
}