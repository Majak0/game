package fr.devops.shared.ingame.event;

import java.util.Map;

public record EntityModifiedEvent(int entityId, Map<String, Object> valuesChanges) implements IngameEvent {
}
