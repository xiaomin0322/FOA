package org.foa.data.optiondata;

import org.foa.entity.Option;
import org.foa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author 王川源
 */
public interface OptionDAO extends JpaRepository<Option, Long>, OptionCustom{

    /**
     * 根据期权合约简称得到最新的期权信息
     * @param optionAbbr 期权合约简称
     * @return
     */
    Option findFirstByOptionAbbrOrderByTimeDesc(String optionAbbr);

    /**
     * 根据期权合约简称获得某个时间点之前的最新记录
     * @param optionAbbr 期权合约简称
     * @param time
     * @return
     */
    Option findFirstByOptionAbbrAndTimeBeforeOrderByTimeDesc(String optionAbbr, LocalDateTime time);

    /**
     * 得到某个期权合约的所有历史信息
     * @param optionAbbr 期权合约简称
     * @return
     */
    List<Option> findByOptionAbbr(String optionAbbr);

    /**
     * 根据期权合约简称得到某个时间点之前的所有该合约信息
     * @param optionAbbr 期权合约简称
     * @param time
     * @return
     */
    List<Option> findByOptionAbbrAndTimeBefore(String optionAbbr, LocalDateTime time);

    /**
     * 返回用户期权池内的所有期权
     * 返回的都是每个期权的最新信息
     * @param userId 用户账号
     * @return
     */
    @Query(value = "SELECT o1.* FROM Option_ o1, (SELECT optionAbbr, MAX('time') as lastUpdateTime FROM Option_ GROUP BY optionAbbr) o2 WHERE " +
            "o1.optionAbbr = o2.optionAbbr AND o1.time = o2.lastUpdateTime AND o1.optionAbbr IN " +
            "(SELECT optionAbbr FROM Subscription WHERE userId = ?1)", nativeQuery = true)
    List<Option> findInterestedOptions(String userId);

    /**
     * 返回对该期权感兴趣的所有用户
     * @param optionAbbr 期权合约简称
     * @return
     */
    @Query("select usr from User usr where usr.userId in " +
            "(select distinct s.userId from Subscription s where s.optionAbbr = ?1)")
    List<User> findInterestingUsers(String optionAbbr);
}