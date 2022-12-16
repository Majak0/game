package fr.devops.shared.ingame.event;

public record EntityMoveEvent(int entityId, double x, double y, double xspeed, double yspeed) implements IngameEvent{
}
