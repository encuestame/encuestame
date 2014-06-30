package org.encuestame.utils.web.stats;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HashTagListGraphData implements Serializable, Comparable<HashTagListGraphData>{

    /** Serial **/
    private static final long serialVersionUID = -3813216985433784846L;

    /**
     * Month of the year
     ***/
    @JsonProperty(value = "month")
    private Integer month;

    /**
     * Number of elements
     **/
    @JsonProperty(value = "value")
    private Long value;

    /**
     * Year in numbers
     */
    @JsonProperty(value = "day")
    private Integer day;

    /**
     * Year in numbers
     */
    @JsonProperty(value = "year")
    private Integer year;

    /**
     * @return the month
     */
    @JsonIgnore
    public Integer getMonth() {
        return month;
    }

    /**
     * @param month the month to set
     */
    public void setMonth(Integer month) {
        this.month = month;
    }

    /**
     * @return the value
     */
    @JsonIgnore
    public Long getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Long value) {
        this.value = value;
    }

    /**
     * @return the year
     */
    @JsonIgnore
    public Integer getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }


    /**
     * @return the day
     */
    public Integer getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(Integer day) {
        this.day = day;
    }

    /**
     * Compare by month and year is enough
     */
    @Override
    public int compareTo(HashTagListGraphData o) {
        int day = this.day.compareTo(o.day);
        int year_ = this.year.compareTo(o.getYear());
        int month = this.month.compareTo(o.getMonth());
        int second = (year_ == 0 ? month : year_);
        return second == 0 ? second : day;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((day == null) ? 0 : day.hashCode());
        result = prime * result + ((month == null) ? 0 : month.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        HashTagListGraphData other = (HashTagListGraphData) obj;
        if (day == null) {
            if (other.day != null)
                return false;
        } else if (!day.equals(other.day))
            return false;
        if (month == null) {
            if (other.month != null)
                return false;
        } else if (!month.equals(other.month))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        return true;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "HashTagListGraphData [month=" + month + ", value=" + value
                + ", day=" + day + ", year=" + year + "]";
    }
}