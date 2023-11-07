package lotto.domain;

import java.util.Arrays;
import java.util.function.BiPredicate;

/*
* - 당첨은 1등부터 5등까지 있다. 당첨 기준과 금액은 아래와 같다.
    - 1등: 6개 번호 일치 / 2,000,000,000원
    - 2등: 5개 번호 + 보너스 번호 일치 / 30,000,000원
    - 3등: 5개 번호 일치 / 1,500,000원
    - 4등: 4개 번호 일치 / 50,000원
    - 5등: 3개 번호 일치 / 5,000원
3개 일치 (5,000원) - 1개
4개 일치 (50,000원) - 0개
5개 일치 (1,500,000원) - 0개
5개 일치, 보너스 볼 일치 (30,000,000원) - 0개
6개 일치 (2,000,000,000원) - 0개

 */
public enum PrizeRankConstants {
    RANK_5TH("3개 일치 (5,000원)", (numberOfMatched, isBonusMatched) -> {
        return (numberOfMatched == 3);
    }),
    RANK_4TH("4개 일치 (50,000원)", (numberOfMatched, isBonusMatched) -> {
        return (numberOfMatched == 4);
    }),
    RANK_3RD("5개 일치 (1,500,000원)", (numberOfMatched, isBonusMatched) -> {
        return (!isBonusMatched && numberOfMatched == 5);
    }),
    RANK_2ND("5개 일치, 보너스 볼 일치 (30,000,000원)", (numberOfMatched, isBonusMatched) -> {
        return (isBonusMatched && numberOfMatched == 5);
    }),
    RANK_1ST("6개 일치 (5,000원)", (numberOfMatched, isBonusMatched) -> {
        return (numberOfMatched == 6);
    });


    private final String message;
    private final BiPredicate<Integer, Boolean> isMatched;

    PrizeRankConstants(String message, BiPredicate<Integer, Boolean> isMatched) {
        this.message = message;
        this.isMatched = isMatched;
    }

    public String getMessage() {
        return message;
    }

    public static PrizeRankConstants getRankConstant(Integer numberOfMatched, boolean isBonusMatched) {
        return Arrays.stream(PrizeRankConstants.values())
                .filter(rank -> rank.isMatched.test(numberOfMatched, isBonusMatched))
                .findAny()
                .orElse(null);
    }
}
