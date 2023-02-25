package fr.devops.shared.ingame.control;

import fr.devops.shared.service.IService;

public interface IPlayerController extends IService{

	public byte getXAxis();
	
	public boolean isJumping();
	
}
