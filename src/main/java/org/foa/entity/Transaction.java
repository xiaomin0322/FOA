package org.foa.entity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author miaomuzhi
 * @since 2018/8/9
 */
@Entity
public class Transaction {

    private LocalDateTime time;

    @OneToMany
    private List<Option> portfolio;

    private double profit;



    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<Option> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(List<Option> portfolio) {
        this.portfolio = portfolio;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }
}