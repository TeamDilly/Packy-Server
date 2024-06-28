package com.dilly.gift.application.strategy;

import static com.dilly.gift.domain.giftbox.MemberRole.POTENTIAL_RECEIVER;
import static com.dilly.gift.domain.giftbox.MemberRole.RECEIVER;
import static com.dilly.gift.domain.giftbox.MemberRole.SENDER;
import static com.dilly.gift.domain.giftbox.MemberRole.STRANGER;

import com.dilly.gift.domain.giftbox.MemberRole;
import java.util.EnumMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class GiftBoxActionProvider {

    private final Map<MemberRole, GiftBoxStrategy> giftBoxActions;

    public GiftBoxActionProvider(
        final SenderStrategy senderStrategy,
        final ReceiverStrategy receiverStrategy,
        final PotentialReceiverStartegy potentialReceiverStartegy,
        final StrangerStrategy strangerStrategy
    ) {
        this.giftBoxActions = new EnumMap<>(MemberRole.class);
        this.giftBoxActions.put(SENDER, senderStrategy);
        this.giftBoxActions.put(RECEIVER, receiverStrategy);
        this.giftBoxActions.put(POTENTIAL_RECEIVER, potentialReceiverStartegy);
        this.giftBoxActions.put(STRANGER, strangerStrategy);
    }

    public GiftBoxStrategy getStrategy(MemberRole memberRole) {
        return giftBoxActions.get(memberRole);
    }
}
