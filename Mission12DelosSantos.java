import robocode.*;
import java.awt.Color;
import robocode.util.Utils.*;
// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html
/**
 * Mission12DelosSantos - a robot by Allan Delos Santos
 */
public class Mission12DelosSantos extends Robot
{
    public void run()
    {
        double moveAmount; // How much to move
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);
        setBodyColor(Color.red);
        setGunColor(Color.black);
        setRadarColor(Color.yellow);
        setBulletColor(Color.green);
        // Initialize gun turn speed to gotta go fast speed
        int gunIncrement = 5;
        // Initialize moveAmount to the maximum possible for this battlefield.
        moveAmount = Math.max(getBattleFieldWidth(), getBattleFieldHeight());
        turnLeft(getHeading() % 90); //adjust heading to stay perpendicular to
        wall
        ahead(moveAmount);
        turnGunRight(90);
        turnRight(90);
    // Robot main loop, spin gun, and scan
    while(true)
    {
        for (int i = 0; i < 30; i++) {
            turnGunLeft(gunIncrement);
        }
        gunIncrement *= -1;
        ahead(moveAmount);
        for (int i = 0; i < 30; i++)
        {
            scan();
        }
    }
}
    public void onScannedRobot(ScannedRobotEvent e)
    {
        //fire depending on heat
        if (getGunHeat() == 0)
        {
            fire(Math.min(3, getEnergy() - .1));
        }
    }
    public void onHitByBullet(HitByBulletEvent e)
    {
        //try to dodge and check for who shot
        back(25);
        scan();
    }
    //mainting perpendicular movement along wall
    public void onHitWall(HitWallEvent e)
    {
        turnRight(90);
    }
    //judge distance on firing
    public void smartFire(double getDistance) 
    {
        if (getDistance > 200 || getEnergy() < 15)
        {
            fire(1);
        }
        else if (getDistance > 50)
        {
            fire(2);
        }
        else
        {
            fire(3);
        }
    }
    //celebrate that robot hasnt lost yet
    public void onRobotDeath(RobotDeathEvent e)
    {
        int deathToll = 0;
        deathToll++;
        if (deathToll >=4 )
        {
            turnLeft(720);
        }
        else
        {
            turnLeft(360);
        }
    }
    //try to avoid otherwise ram
    public void onHitRobot(HitRobotEvent e)
    {
        if (e.getBearing() > -90 && e.getBearing() < 90)
        {
            back(100);
        }
        else
        {
            ahead(100);
        }
    }
    //if missed try to rescan to recalculate where to shoot
    public void onBulletMissed (BulletMissedEvent e)
    {
        scan();
    }
    //try to readjust position and shoot
    public void onBulletHitBullet (BulletHitBulletEvent e)
    {
        ahead(50);
        scan();
        fire(100);
    }
    //victory dance
    public void onWin(WinEvent e)
    {
        //code dance
        ahead(50);
        ahead(25);
        back(25);
        back(50);
        turnLeft(15);
        turnRight(15);
        turnLeft(15);
        turnRight(15);
        fire(1);
        fire(2);
        fire(3);
    }
}