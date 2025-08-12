# Bricker Game

![Hero Screenshot](assets/hero-gameplay.png)
<!-- צילום מסך "כללי" של המשחק בפעולה: פאדל, כדור, קיר לבנים צבעוני -->

## Project Description
Bricker is a modern twist on the classic brick-breaking arcade game. The player controls a horizontal paddle, deflects a ball, and clears a wall of bricks. Hits may trigger special effects such as spawning a second paddle, adding extra balls, or zooming the camera for dramatic impact. Lives are shown both as heart icons and as a numeric counter that update in real time.

## Features
- Game objects: ball, bricks, extra-life hearts, second paddle, graphic and numeric life counters  
- Special brick strategies: extra paddle, extra balls, camera zoom, double strategy (combines two effects)  
- Centralized game management for object lifecycle, collisions, and state updates  
- Responsive paddle control with precise reflection angles  
- Scalable architecture for new stages, strategies, and game objects

## Gallery
| ![Hero Gameplay](assets/hero-gameplay.png) | ![Extra Life Drop](assets/extra-life.png) | ![Near Win](assets/near-win.png) |
|---|---|---|
| *תצוגה כללית של המשחק בפעולה* | *לב נופל לאחר פגיעה בלבנה מיוחדת* | *רגע "כמעט ניצחון" עם מעט לבנים שנותרו* |

## How It Works
- BrickerGameManager מנהל יצירה/הסרה של אובייקטים, התנגשויות, מצב חיים וניקוד  
- StrategyFactory מחליט אילו אסטרטגיות מופעלות על לבנים מיוחדות ומגביל קומבינציות מורכבות מדי  
- DoubleStrategy מאפשר להחיל שתי אסטרטגיות יחד, למתח והפתעה מבוקרים

## Technologies
Java, DanoGameLab, IntelliJ IDEA

## Extensions (ideas)
Multiplayer, new stages, unbreakable bricks, additional strategies, power-ups, and advanced UI.

## Credits
Developed by Roy Karif and Yuval Jacobs. Thanks to DanoGameLab for the framework.
