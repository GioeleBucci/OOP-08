package it.unibo.deathnote;

import static org.junit.jupiter.api.Assertions.assertEquals;  
import static org.junit.jupiter.api.Assertions.assertFalse;   
import static org.junit.jupiter.api.Assertions.assertNotNull; 
import static org.junit.jupiter.api.Assertions.assertTrue;    
import static org.junit.jupiter.api.Assertions.fail;          

import java.util.List;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.api.DeathNote;
import it.unibo.deathnote.impl.DeathNoteImpl;

class TestDeathNote {

  DeathNoteImpl deathnote = new DeathNoteImpl();

  @Test
  void noRuleZeroAndBelow() {
    for (final int index : List.of(0, -1)) {
      try {
        deathnote.getRule(index);
        fail();
      } catch (IllegalArgumentException e) {
        assertFalse(e.getMessage().isBlank());
      }
    }
  }

  @Test
  void noRuleEmptyOrNull() {
    for (final String rule : DeathNote.RULES) {
      assertNotNull(rule);
      assertFalse(rule.isBlank());
    }
  }

  @Test
  void humanDies() {
    final String name = "Light Yagami";
    assertFalse(deathnote.isNameWritten(name));
    deathnote.writeName(name);
    assertTrue(deathnote.isNameWritten(name));
    assertFalse(deathnote.isNameWritten(""));
  }

  @Test
  void getDeathCauseWithinTimeLimit() throws InterruptedException {
    // check that writing a cause of death before writing a name throws the correct
    // exception
    try {
      deathnote.writeDeathCause("seizure");
      fail("");
    } catch (IllegalStateException e) { 
      assertFalse(e.getMessage().isBlank());
    }
    // kill by heart attack (no death cause written)
    final String name = "Misa Amane";
    deathnote.writeName(name);
    assertEquals("heart attack", deathnote.getDeathCause(name));
    // write another name and set the cause of death to "karting accident"
    final String name2 = "L";
    final String deathCause = "karting incident";
    deathnote.writeName(name2);
    assertTrue(deathnote.writeDeathCause(deathCause));
    assertEquals(deathCause, deathnote.getDeathCause(name2));
    Thread.sleep(100);
    // try to change the cause of death and verify it did not change
    deathnote.writeDeathCause("slip on a banana peel");
    assertEquals(deathCause, deathnote.getDeathCause(name2));
  }

  @Test
  void testDeathDetails() throws InterruptedException {
    final String name = "Someone";
    deathnote.writeName(name);
    assertTrue(deathnote.getDeathDetails(name).isBlank());
    final String deathDetails = "ran for too long";
    assertTrue(deathnote.writeDetails(deathDetails));
    assertEquals(deathDetails, deathnote.getDeathDetails(name));
    final String name2 = "Someone else";
    deathnote.writeName(name2);
    Thread.sleep(6100);
    String defaultDetails = deathnote.getDeathDetails(name2);
    assertFalse(deathnote.writeDetails(deathDetails));
    assertEquals(deathnote.getDeathDetails(name2), defaultDetails);
  }
}
