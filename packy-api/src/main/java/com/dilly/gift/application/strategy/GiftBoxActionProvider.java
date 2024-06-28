package com.dilly.gift.application.strategy;

import com.dilly.gift.domain.giftbox.MemberRole;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GiftBoxActionProvider {

    private final Map<MemberRole, GiftBoxStrategy> giftBoxActions;

    public GiftBoxActionProvider(
        final SenderStrategy senderStrategy,
        final ReceiverStrategy receiverStrategy
    ) {
        this.giftBoxActions = new EnumMap<>(MemberRole.class);
        this.giftBoxActions.put(MemberRole.SENDER, senderStrategy);
        this.giftBoxActions.put(MemberRole.RECEIVER, receiverStrategy);
    }

    public GiftBoxStrategy getStrategy(MemberRole memberRole) {
        return giftBoxActions.get(memberRole);
    }
}
