package org.usfirst.frc.team871.subsystems.navigation;

import java.util.Arrays;
import java.util.List;

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
		
		FieldSetup setup5 = new ManualFieldSetup("LLL", false, false, false, false, false, false, 0);
		WaypointSelector ws5 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup5);
		ws5.determineOffLimits();
		Assert.assertTrue(!ws5.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertTrue(!ws5.isOfflimits(WaypointPosition.SCALE_R));
		Assert.assertTrue(!ws5.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertTrue(!ws5.isOfflimits(WaypointPosition.SWITCH_R));
		
		FieldSetup setup6 = new ManualFieldSetup("LLL", true, true, true, true, false, false, 0);
		WaypointSelector ws6 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup6);
		ws6.determineOffLimits();
		Assert.assertTrue(ws6.isOfflimits(WaypointPosition.SCALE_L));
		Assert.assertTrue(ws6.isOfflimits(WaypointPosition.SCALE_R));
		Assert.assertTrue(ws6.isOfflimits(WaypointPosition.SWITCH_L));
		Assert.assertTrue(ws6.isOfflimits(WaypointPosition.SWITCH_R));
		
	}
	
	
	private class TestCase {
		String fc;
		boolean[] limits;
		boolean[] inboard;
		int startpos;
		
		WaypointPosition end;
		String path;
		
		TestCase(String fc, boolean[] limits, boolean[] ib, int sp, WaypointPosition end, String path) {
			this.fc = fc;
			this.limits = limits;
			this.inboard = ib;
			this.startpos = sp;
			this.end = end;
			this.path = path;
		}
		
		ManualFieldSetup getSetup() {
			return new ManualFieldSetup(fc, limits[0], limits[1], limits[2], limits[3], inboard[0], inboard[1], startpos);
		}
	}
	
	@Test
	public void testDeterminePreferance() {
		WaypointProviderFactory.DEFAULT.init(null, null, null);
		
		FieldSetup setup1 = new ManualFieldSetup("LLL", false, false, false, false, true, false, 0);
		WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
		ws1.determinePreferance();
		Assert.assertEquals(ws1.getSide(WaypointPosition.SWITCH_R), WaypointSide.INBOARD);
		Assert.assertEquals(ws1.getSide(WaypointPosition.SWITCH_L), WaypointSide.OUTBOARD);
		
		FieldSetup setup2 = new ManualFieldSetup("LLL", false, false, false, false, false, true, 0);
		WaypointSelector ws2 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup2);
		ws2.determinePreferance();
		Assert.assertEquals(ws2.getSide(WaypointPosition.SWITCH_R), WaypointSide.OUTBOARD);
		Assert.assertEquals(ws2.getSide(WaypointPosition.SWITCH_L), WaypointSide.INBOARD);
		
		FieldSetup setup3 = new ManualFieldSetup("LLL", false, false, false, false, false, false, 0);
		WaypointSelector ws3 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup3);
		ws3.determinePreferance();
		Assert.assertEquals(ws3.getSide(WaypointPosition.SWITCH_R), WaypointSide.OUTBOARD);
		Assert.assertEquals(ws3.getSide(WaypointPosition.SWITCH_L), WaypointSide.OUTBOARD);
		
		FieldSetup setup4 = new ManualFieldSetup("LLL", false, false, false, false, true, true, 0);
		WaypointSelector ws4 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup4);
		ws4.determinePreferance();
		Assert.assertEquals(ws4.getSide(WaypointPosition.SWITCH_R), WaypointSide.INBOARD);
		Assert.assertEquals(ws4.getSide(WaypointPosition.SWITCH_L), WaypointSide.INBOARD);
		
	}
	
	@Test
	public void testFindPath() {
		WaypointProviderFactory.DEFAULT.init(null, null, null);
		
		FieldSetup setup1 = new ManualFieldSetup("LLL", false, false, false, false, false, false, 0);
		WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
		
		for(WaypointArrayPositionWrapper wp : WaypointProviderFactory.DEFAULT.getWrappers()) {
			System.out.println(wp.getName() + " has " + wp.getStartPosition() + ", " + wp.getEndPosition() + " " + wp.getSide());
			if(wp.getSide() == null) {
				WaypointProvider found = ws1.findPath(wp.getStartPosition(), wp.getEndPosition(), WaypointSide.INBOARD);
				System.out.println(wp.getName() + "(IN) -> " + found.getName() + "\n");
				Assert.assertTrue(found.getName().equals(wp.getName()));
				
				WaypointProvider found2 = ws1.findPath(wp.getStartPosition(), wp.getEndPosition(), WaypointSide.OUTBOARD);
				System.out.println(wp.getName() + "(OUT) -> " + found2.getName() + "\n");
				Assert.assertTrue(found2.getName().equals(wp.getName()));
			}else {
				WaypointProvider found = ws1.findPath(wp.getStartPosition(), wp.getEndPosition(), wp.getSide());
				System.out.println(wp.getName() + " -> " + found.getName() + "\n");
				Assert.assertTrue(found.getName().equals(wp.getName()));
			}
		}
		
	}
	
	final List<TestCase> cases = Arrays.asList(new TestCase[] {
					//	 Field,                 LSc  , RSc  , LSw  , RSw                   , RIB,   LIB,    SP  , Correct End,             Corr Path
			new TestCase("LLL", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 0, WaypointPosition.SCALE_L   , "LScale"),
			new TestCase("LLL", new boolean[] { true , false, false, false}, new boolean[] { false, false }, 0, WaypointPosition.SWITCH_L  , "LSwitch"),
			new TestCase("LLL", new boolean[] { true , false, true , false}, new boolean[] { false, false }, 0, WaypointPosition.AUTOLINE_L, "LAutoLine"),
			new TestCase("LLL", new boolean[] { false, false, true , false}, new boolean[] { false, false }, 0, WaypointPosition.SCALE_L   , "LScale"),
			new TestCase("LLL", new boolean[] { false, false, false, false}, new boolean[] { false, true  }, 0, WaypointPosition.SCALE_L   , "LScale"),
			new TestCase("LLL", new boolean[] { true , false, false, false}, new boolean[] { false, true  }, 0, WaypointPosition.SWITCH_L  , "LSwitch"),
			new TestCase("LLL", new boolean[] { true , false, true , false}, new boolean[] { false, true  }, 0, WaypointPosition.AUTOLINE_L, "LAutoLine"),
			new TestCase("LLL", new boolean[] { false, false, true , false}, new boolean[] { false, true  }, 0, WaypointPosition.SCALE_L   , "LScale"),
			
			new TestCase("LLL", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 1, WaypointPosition.SWITCH_L  , "MStartLSwitch"),
			new TestCase("LLL", new boolean[] { true , false, false, false}, new boolean[] { false, false }, 1, WaypointPosition.SWITCH_L  , "MStartLSwitch"),
			new TestCase("LLL", new boolean[] { true , false, true , false}, new boolean[] { false, false }, 1, WaypointPosition.AUTOLINE_M, "MAutoLine"),
			new TestCase("LLL", new boolean[] { false, false, true , false}, new boolean[] { false, false }, 1, WaypointPosition.SCALE_L   , "MStartLScale"),
			new TestCase("LLL", new boolean[] { false, false, false, false}, new boolean[] { false, true  }, 1, WaypointPosition.SWITCH_L  , "MStartLSwitch"),
			new TestCase("LLL", new boolean[] { true , false, false, false}, new boolean[] { false, true  }, 1, WaypointPosition.SWITCH_L  , "MStartLSwitch"),
			new TestCase("LLL", new boolean[] { true , false, true , false}, new boolean[] { false, true  }, 1, WaypointPosition.AUTOLINE_M, "MAutoLine"),
			new TestCase("LLL", new boolean[] { false, false, true , false}, new boolean[] { false, true  }, 1, WaypointPosition.SCALE_L   , "MStartLScale"),
			
			new TestCase("LLL", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 2, WaypointPosition.SCALE_L   , "RStartLScale"),
			new TestCase("LLL", new boolean[] { false, false, false, false}, new boolean[] { false, true  }, 2, WaypointPosition.SCALE_L   , "RStartLScale"),
			new TestCase("LLL", new boolean[] { true , false, false, false}, new boolean[] { false, false }, 2, WaypointPosition.SWITCH_L  , "RStartLSwitch"),
			new TestCase("LLL", new boolean[] { true , false, false, false}, new boolean[] { false, true  }, 2, WaypointPosition.SWITCH_L  , "RStartLSwitchDirect"),
			new TestCase("LLL", new boolean[] { true , false, true , false}, new boolean[] { false, false }, 2, WaypointPosition.AUTOLINE_R, "RAutoLine"),
			new TestCase("LLL", new boolean[] { true , false, true , false}, new boolean[] { false, true  }, 2, WaypointPosition.AUTOLINE_R, "RAutoLine"),
			new TestCase("LLL", new boolean[] { false, false, true , false}, new boolean[] { false, false }, 2, WaypointPosition.SCALE_L   , "RStartLScale"),
			new TestCase("LLL", new boolean[] { false, false, true , false}, new boolean[] { false, true  }, 2, WaypointPosition.SCALE_L   , "RStartLScale"),
			
			new TestCase("RRR", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 2, WaypointPosition.SCALE_R   , "RScale"),
			new TestCase("RRR", new boolean[] { false, true , false, false}, new boolean[] { false, false }, 2, WaypointPosition.SWITCH_R  , "RSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, true }, new boolean[] { false, false }, 2, WaypointPosition.AUTOLINE_R, "RAutoLine"),
			new TestCase("RRR", new boolean[] { false, false, false, true }, new boolean[] { false, false }, 2, WaypointPosition.SCALE_R   , "RScale"),
			new TestCase("RRR", new boolean[] { false, false, false, false}, new boolean[] { false, true  }, 2, WaypointPosition.SCALE_R   , "RScale"),
			new TestCase("RRR", new boolean[] { false, true , false, false}, new boolean[] { false, true  }, 2, WaypointPosition.SWITCH_R  , "RSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, true }, new boolean[] { false, true  }, 2, WaypointPosition.AUTOLINE_R, "RAutoLine"),
			new TestCase("RRR", new boolean[] { false, false, false, true }, new boolean[] { false, true  }, 2, WaypointPosition.SCALE_R   , "RScale"),
			
			new TestCase("RRR", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 1, WaypointPosition.SWITCH_R  , "MStartRSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, false}, new boolean[] { false, false }, 1, WaypointPosition.SWITCH_R  , "MStartRSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, true }, new boolean[] { false, false }, 1, WaypointPosition.AUTOLINE_M, "MAutoLine"),
			new TestCase("RRR", new boolean[] { false, false, false, true }, new boolean[] { false, false }, 1, WaypointPosition.SCALE_R   , "MStartRScale"),
			new TestCase("RRR", new boolean[] { false, false, false, false}, new boolean[] { false, true  }, 1, WaypointPosition.SWITCH_R  , "MStartRSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, false}, new boolean[] { false, true  }, 1, WaypointPosition.SWITCH_R  , "MStartRSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, true }, new boolean[] { false, true  }, 1, WaypointPosition.AUTOLINE_M, "MAutoLine"),
			new TestCase("RRR", new boolean[] { false, false, false, true }, new boolean[] { false, true  }, 1, WaypointPosition.SCALE_R   , "MStartRScale"),
			
			new TestCase("RRR", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 0, WaypointPosition.SCALE_R   , "LStartRScale"),
			new TestCase("RRR", new boolean[] { false, true , false, false}, new boolean[] { false, false }, 0, WaypointPosition.SWITCH_R  , "LStartRSwitch"),
			new TestCase("RRR", new boolean[] { false, true , false, true }, new boolean[] { false, false }, 0, WaypointPosition.AUTOLINE_L, "LAutoLine"),
			new TestCase("RRR", new boolean[] { false, false, false, true }, new boolean[] { false, false }, 0, WaypointPosition.SCALE_R   , "LStartRScale"),
			new TestCase("RRR", new boolean[] { false, false, false, false}, new boolean[] { false, true  }, 0, WaypointPosition.SCALE_R   , "LStartRScale"),
			new TestCase("RRR", new boolean[] { false, true , false, false}, new boolean[] { false, true  }, 0, WaypointPosition.SWITCH_R  , "LStartRSwitchDirect"),
			new TestCase("RRR", new boolean[] { false, true , false, true }, new boolean[] { false, true  }, 0, WaypointPosition.AUTOLINE_L, "LAutoLine"),
			new TestCase("RRR", new boolean[] { false, false, false, true }, new boolean[] { false, true  }, 0, WaypointPosition.SCALE_R   , "LStartRScale"),
			
			new TestCase("LRL", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 0, WaypointPosition.SWITCH_L  , "LSwitch"),
			new TestCase("LRL", new boolean[] { false, false, true , false}, new boolean[] { false, false }, 0, WaypointPosition.SCALE_R   , "LStartRScale"),
			new TestCase("LRL", new boolean[] { false, true , true , false}, new boolean[] { false, false }, 0, WaypointPosition.AUTOLINE_L, "LAutoLine"),
			new TestCase("LRL", new boolean[] { false, true , false, false}, new boolean[] { false, false }, 0, WaypointPosition.SWITCH_L  , "LSwitch"),
			
			new TestCase("RLR", new boolean[] { false, false, false, false}, new boolean[] { false, false }, 2, WaypointPosition.SWITCH_R  , "RSwitch"),
			new TestCase("RLR", new boolean[] { false, false, false, true }, new boolean[] { false, false }, 2, WaypointPosition.SCALE_L   , "RStartLScale"),
			new TestCase("RLR", new boolean[] { true , false, false, true }, new boolean[] { false, false }, 2, WaypointPosition.AUTOLINE_R, "RAutoLine"),
			new TestCase("RLR", new boolean[] { true , false, false, false}, new boolean[] { false, false }, 2, WaypointPosition.SWITCH_R  , "RSwitch"),
	});
	
	@Test
	public void testChooseEndpoint() {
		WaypointProviderFactory.DEFAULT.init(null, null, null);
		
		for(TestCase t : cases) {
			FieldSetup setup1 = t.getSetup();
			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
			ws1.setup();
			Assert.assertEquals(t.end, ws1.chooseEndpoint());
			Assert.assertNotNull(ws1.getPath());
			Assert.assertEquals(t.path, ws1.getPath().getName());
		}
		
//		// Start L
//		
//		 // LLL
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", false, false, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LScale", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", true, false, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LSwitch", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", false, false, true, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LScale", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", true, false, true, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", true, true, true, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", true, false, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LLL", true, true, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		 // RRR
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, true, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, true, false, false, true, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_R, ws1.chooseEndpoint());
//			Assert.assertEquals("LStartRSwitchDirect", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, false, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_R, ws1.chooseEndpoint());
//			Assert.assertEquals("LStartRScale", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, true, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_R, ws1.chooseEndpoint());
//			Assert.assertEquals("LStartRSwitch", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, true, false, false, true, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_R, ws1.chooseEndpoint());
//			Assert.assertEquals("LStartRSwitchDirect", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, false, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_R, ws1.chooseEndpoint());
//			Assert.assertEquals("LStartRScale", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, true, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", true, true, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", false, true, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RRR", true, true, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		 // LRL
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, true, true, false, false, true, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, false, false, true, false, true, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LSwitch", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, false, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, false, true, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_R, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, true, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, true, true, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", true, true, true, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", false, true, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("LRL", true, true, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		 // RLR
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, false, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", false, false, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LScale", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, false, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//			Assert.assertEquals("LAutoLine", ws1.getPath().getName());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", false, false, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, false, false, false, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SWITCH_R, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", false, false, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.SCALE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, false, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, true, false, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, false, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		{
//			FieldSetup setup1 = new ManualFieldSetup("RLR", true, true, true, true, false, false, 0);
//			WaypointSelector ws1 = new WaypointSelector(WaypointProviderFactory.DEFAULT.getWrappers(), setup1);
//			ws1.setup();
//			Assert.assertEquals(WaypointPosition.AUTOLINE_L, ws1.chooseEndpoint());
//		}
//		
//		// Start M
//		
//		// Start R
//		
	}
	
	
}
