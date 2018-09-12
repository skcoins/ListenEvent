/**
 * 
 */
package com.softisland.handler;

import com.softisland.model.TranscationEvent;
import com.softisland.service.TranscationEventService;

import lombok.Builder;
import lombok.Data;

/**
 * @author Administrator
 *
 */
@Data
@Builder
public class RecordEventHandler implements Runnable{
	
	private TranscationEventService transcationEventService;
	
	private TranscationEvent transcationEvent;
	
	public RecordEventHandler(TranscationEventService transcationEventService,TranscationEvent transcationEvent){
		this.transcationEventService = transcationEventService;
		this.transcationEvent = transcationEvent;
	}

	@Override
	public void run() {
		transcationEventService.insertTranscationEvent(transcationEvent);
	}

}
