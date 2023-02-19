package fr.devops.shared.network.request;

import fr.devops.shared.ingame.entity.EntityType;

public record EntitySpawnRequest(EntityType type, double x, double y) implements IRequest{
}
