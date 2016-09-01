 using System;
 using System.Collections.Generic;
 using System.Linq;
 using Microsoft.Xna.Framework;
 using Microsoft.Xna.Framework.Audio;
 using Microsoft.Xna.Framework.Content;
 using Microsoft.Xna.Framework.GamerServices;
 using Microsoft.Xna.Framework.Graphics;
 using Microsoft.Xna.Framework.Input;
 using Microsoft.Xna.Framework.Media;
 
 namespace kr.co.bcu.propio.game.xbox.pad
 {
     /// <summary>
     /// This is the main type for your game
     /// </summary>
     public class Game1 : Microsoft.Xna.Framework.Game
     {
         GraphicsDeviceManager graphics;
         SpriteBatch spriteBatch;
         SpriteFont spriteFont;                 //생성한 폰트 선언

         private string printText = "";         //어느 버튼이 눌렸는가를 출력 할 문자열
         private float x = 20;                  //텍스트 출력 X좌표
         private float y = 20;                  //텍스트 출력 Y좌표

         private float sensitivityOne = 200;      //첫번째 스틱 감도
         private float sensitivityTwo = 100;      //두번째 스틱 감도
 
         public Game1()
         {
             graphics = new GraphicsDeviceManager(this);
             Content.RootDirectory = "Content";
         }
 
         /// <summary>
         /// Allows the game to perform any initialization it needs to before starting to run.
         /// This is where it can query for any required services and load any non-graphic
         /// related content.  Calling base.Initialize will enumerate through any components
         /// and initialize them as well.
         /// </summary>
         protected override void Initialize()
         {
             // TODO: Add your initialization logic here
             graphics.IsFullScreen = false;
             graphics.PreferredBackBufferWidth = 1280;
             graphics.PreferredBackBufferHeight = 800;
             graphics.ApplyChanges();

             base.Initialize();
         }
 
         /// <summary>
         /// LoadContent will be called once per game and is the place to load
         /// all of your content.
         /// </summary>
         protected override void LoadContent()
         {
             // Create a new SpriteBatch, which can be used to draw textures.
             spriteBatch = new SpriteBatch(GraphicsDevice);
             spriteFont = Content.Load<SpriteFont>("Arial");    //생성한 폰트의 이름으로 초기화
 
             // TODO: use this.Content to load your game content here
         }
 
         /// <summary>
         /// UnloadContent will be called once per game and is the place to unload
         /// all content.
         /// </summary>
         protected override void UnloadContent()
         {
             // TODO: Unload any non ContentManager content here
         }
 
         /// <summary>
         /// Allows the game to run logic such as updating the world,
         /// checking for collisions, gathering input, and playing audio.
         /// </summary>
         /// <param name="gameTime">Provides a snapshot of timing values.</param>
         protected override void Update(GameTime gameTime)
         {
             // Allows the game to exit
             if (GamePad.GetState(PlayerIndex.One).Buttons.Back == ButtonState.Pressed)
                 this.Exit();
 
             // TODO: Add your update logic here

             //게임 컨트롤러에 대한 정보를 가지고 있는 객체 할당
             GamePadState gamePadState = GamePad.GetState(PlayerIndex.One);
             
             //각 버튼 테스트
             if (gamePadState.Buttons.A == ButtonState.Pressed)
             {
                 printText = "A";
             }

             if (gamePadState.Buttons.B == ButtonState.Pressed)
             {
                 printText = "B";
             }

             //스틱으로 출력된 문자 이동 구현
             x += (gamePadState.ThumbSticks.Left.X * (sensitivityOne * 0.01f));
             y += -((gamePadState.ThumbSticks.Left.Y) * (sensitivityOne * 0.01f));

             base.Update(gameTime);
         }
 
         /// <summary>
         /// This is called when the game should draw itself.
         /// </summary>
         /// <param name="gameTime">Provides a snapshot of timing values.</param>
         protected override void Draw(GameTime gameTime)
         {
             GraphicsDevice.Clear(Color.CornflowerBlue);
 
             // TODO: Add your drawing code here
             spriteBatch.Begin();                       //텍스트 출력 시작
             DrawText(printText);                       //텍스트 출력 함수 호출
             spriteBatch.End();                         //텍스트 출력 종료
 
             base.Draw(gameTime);
         }

         /// <summary>
         /// 넘겨 받은 인자값의 내용을 화면에 출력하는 함수
         /// </summary>
         /// <param name="printText">출력 할 텍스트</param>
         private void DrawText(string printText)
         {
             //생성한 폰트로 화면 (20, 30) 지점에 검은색으로 printText의 내용을 출력
             spriteBatch.DrawString(spriteFont, printText, new Vector2(x, y), Color.Black);
         }
     }
 }
