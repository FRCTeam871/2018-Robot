package org.usfirst.frc.team871.subsystems.navigation;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TestWaypointSelection {
	
	@Test 
	public void testSetupConfiguration() {

		WaypointProviderFactory.DEFAULT.init(null, null, null);
		
		FieldSetup setup1 = new ManualFieldSetup("RRR", false, false, false, false, false, false, 0);
		WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
		ws1.setupConfiguration();
		Assert.assertFalse(!ws1.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertFalse(ws1.isOfflimits(WaypointPosition.SWITCH_R));
		Assert.assertFalse(!ws1.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertFalse(ws1.isOfflimits(WaypointPosition.SCALE_R));
		
		FieldSetup setup2 = new ManualFieldSetup("LLL", false, false, false, false, false, false, 0);
		WaypointSelector ws2 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup2);
		ws2.setupConfiguration();
		Assert.assertFalse(ws2.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertFalse(!ws2.isOfflimits(WaypointPosition.SWITCH_R));
		Assert.assertFalse(ws2.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertFalse(!ws2.isOfflimits(WaypointPosition.SCALE_R));
		
		FieldSetup setup3 = new ManualFieldSetup("RLR", false, false, false, false, false, false, 0);
		WaypointSelector ws3 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup3);
		ws3.setupConfiguration();
		Assert.assertFalse(!ws3.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertFalse(ws3.isOfflimits(WaypointPosition.SWITCH_R));
		Assert.assertFalse(ws3.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertFalse(!ws3.isOfflimits(WaypointPosition.SCALE_R));
		
		FieldSetup setup4 = new ManualFieldSetup("LRL", false, false, false, false, false, false, 0);
		WaypointSelector ws4 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup4);
		ws4.setupConfiguration();
		Assert.assertFalse(ws4.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertFalse(!ws4.isOfflimits(WaypointPosition.SWITCH_R));
		Assert.assertFalse(!ws4.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertFalse(ws4.isOfflimits(WaypointPosition.SCALE_R));
	}
	
	@Test
	public void testDetermineOffLimits() {
		WaypointProviderFactory.DEFAULT.init(null, null, null);
		//LLL field config, going to left scale
		FieldSetup setup1 = new ManualFieldSetup("LLL", true, false, false, false, false, false, 0);
		WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
		ws1.determineOffLimits();
		Assert.assertTrue(ws1.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertTrue(!ws1.isOfflimits(WaypointPosition.SCALE_R));
		Assert.assertTrue(!ws1.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertTrue(!ws1.isOfflimits(WaypointPosition.SWITCH_R));
	
		FieldSetup setup2 = new ManualFieldSetup("LLL", false, true, false, false, false, false, 0);
		WaypointSelector ws2 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup2);
		ws2.determineOffLimits();
		Assert.assertTrue(!ws2.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertTrue(ws2.isOfflimits(WaypointPosition.SCALE_R));
		Assert.assertTrue(!ws2.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertTrue(!ws2.isOfflimits(WaypointPosition.SWITCH_R));
	
		FieldSetup setup3 = new ManualFieldSetup("LLL", false, false, true, false, false, false, 0);
		WaypointSelector ws3 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup3);
		ws3.determineOffLimits();
		Assert.assertTrue(!ws3.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertTrue(!ws3.isOfflimits(WaypointPosition.SCALE_R));
		Assert.assertTrue(ws3.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertTrue(!ws3.isOfflimits(WaypointPosition.SWITCH_R));
		
		FieldSetup setup4 = new ManualFieldSetup("LLL", false, false, false, true, false, false, 0);
		WaypointSelector ws4 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup4);
		ws4.determineOffLimits();
		Assert.assertTrue(!ws4.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertTrue(!ws4.isOfflimits(WaypointPosition.SCALE_R));
		Assert.assertTrue(!ws4.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertTrue(ws4.isOfflimits(WaypointPosition.SWITCH_R));
	
	}
	
}
