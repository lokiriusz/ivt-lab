package hu.bme.mit.spaceship;

import static junit.framework.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore primary;
  private TorpedoStore secondary;

  @BeforeEach
  public void init(){
    primary = mock(TorpedoStore.class);
    secondary = mock(TorpedoStore.class);

    this.ship = new GT4500(
            primary,
            secondary
    );
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    verify(primary, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);

    // Act
    ship.fireTorpedo(FiringMode.ALL);

    // Assert
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);
  }

  //Test-1
  // Fire fails without torpedo.
  @Test
  public void testNoTorpedoSingle(){
    //Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(true);

    //Act
    boolean fired = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();

    assertFalse(fired);
  }

  //Test-2
  //Primary store is empty, so secondary store fires.
  @Test
  public void testPrimaryStoreIsEmptySoSecondFiresNow(){
    //Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);

    //Act
    ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).fire(1);
  }

  //Test-3
  //Secondary store is empty, so Primary store fires.
  @Test
  public void testPrimaryStoreFiresLastButSecondStoreIsEmptySoPrimaryFiresNow(){

    //Arrange
    when(primary.isEmpty()).thenReturn(true);
    when(secondary.isEmpty()).thenReturn(false);
    when(secondary.fire(1)).thenReturn(true);

    //Act
    //ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    //verify(primary, times(1)).isEmpty();
    //verify(secondary, times(1)).isEmpty();
    verify(secondary, times(1)).fire(1);

    assertTrue(result);
  }

  //Test-4
  //Fire All Stores No Torpedo
  @Test
  public void testAllStoreNoTorpedo(){

    //Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    //Act
    //ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    verify(primary, times(1)).isEmpty();
    verify(secondary, times(1)).isEmpty();

    assertFalse(result);
  }

  //Test-5
  //Primary and Secondary store are empty, so don't fire.
  @Test
  public void testAllStoreWithTorpedos(){

    //Arrange
    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    //Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    //Assert
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertTrue(result);
  }

  @Test
  public void testAllStoreWiTorpedos(){

    //Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(false);

    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    //Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(primary, times(1)).fire(1);
    verify(secondary, times(1)).fire(1);

    assertTrue(result);
  }

  @Test
  public void testAlloreWiTorpedos(){

    //Arrange
    when(primary.isEmpty()).thenReturn(false);
    when(secondary.isEmpty()).thenReturn(true);

    when(primary.fire(1)).thenReturn(true);
    when(secondary.fire(1)).thenReturn(true);
    //Act
    ship.fireTorpedo(FiringMode.SINGLE);
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    //Assert
    verify(primary, times(2)).fire(1);
    verify(secondary, times(0)).fire(1);

    assertTrue(result);
  }

}
