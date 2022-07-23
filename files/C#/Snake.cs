using System.Security.Cryptography;
using System.Collections.Specialized;
using System.Globalization;
using System;
using System.Linq;
using System.Collections.Generic;
using System.Drawing;
using System.Windows.Forms;

class Program {
    static void Main() {
        MessageBox.Show("はじまるよ～！", "スネーク");
        while (true) {
            var f = new GameWindow();
            f.ShowDialog();
            if (MessageBox.Show("もう一回やる？？", "スネーク", MessageBoxButtons.YesNo) == DialogResult.No) break;
        }
    }
}

class GameWindow : Form {
    private Timer _timer;
    private Snake _snake;
    private Block _food;

    public const int WH = 400; // たてよこの長さ
    public const int GRIDSIZE = 20;


    public GameWindow() {
        var _s = new Size(WH, WH);
        Text = "スネークだよ";
        ShowIcon = false;
        BackColor = Color.Black;
        MaximizeBox = false;
        Size = _s;
        MaximumSize = _s;
        MinimumSize = _s;

        _timer = new Timer() {
            Interval = 100
        };
        _snake = new Snake(new Block(0, 0));
        _food = GetRandomBlock();

        Paint += Form_Paint;
        KeyDown += OnKeyDown;
        _timer.Tick += Update;

        _timer.Start();
    }

    private static Block GetRandomBlock() {
        var r = new Random();
        var x = r.Next(0, WH - GRIDSIZE * 2 + 1);
        var y = r.Next(0, WH - GRIDSIZE * 2 + 1);
        return new Block(x - x % GRIDSIZE, y - y % GRIDSIZE);
    }

    private void OnKeyDown(object sender, KeyEventArgs e) {
        var key = e.KeyCode;
        var dir = _snake.dir;

        switch (key) {
            case Keys.Up: dir = Dir.Up; break;
            case Keys.Down: dir = Dir.Down; break;
            case Keys.Left: dir = Dir.Left; break;
            case Keys.Right: dir = Dir.Right; break;
            default: break;
        }

        _snake.SetDir(dir);
    }

    private void Form_Paint(object sender, PaintEventArgs e) {
        var g = e.Graphics;
        _snake.Draw(g);
        _food.Draw(g);
    }

    private void Update(object sender, EventArgs e) {
        if (_snake.EatingFood(_food)) {
            _snake.AddBlock();
            _food = GetRandomBlock();
            _timer.Interval -= _timer.Interval > 50 ? 5 : 0;
        }

        if (_snake.IsGameOver()) {
            _timer.Stop();
            MessageBox.Show("ゲームオーバー！");
            this.Close();
        }
        _snake.Next();
        Refresh();       
    }


}

class Block {
    public int X { get { return Fix(_x); } set { _x = value; }}
    public int Y { get { return Fix(_y); } set { _y = value; }}

    private int _x;
    private int _y;

    public Block(int x, int y) {
        _x = x; _y = y;
    }

    public void Draw(Graphics g) {
        g.FillRectangle(Brushes.White, new Rectangle(X, Y, GameWindow.GRIDSIZE, GameWindow.GRIDSIZE));
    }

    private int Fix(int pos) {
        if (pos >= 0)
            return pos % GameWindow.WH;
        else
            return GameWindow.WH - (-pos % GameWindow.WH);
    }   
}

class Snake {
    private List<Block> _snake;
    private bool _addBlock = false;

    public Dir dir;

    public Snake(Block first) {
        dir = Dir.Down;
        _snake = new List<Block>();
        _snake.Add(first);
    }

    public void Draw(Graphics g) {
        _snake.ForEach(b => b.Draw(g));
    }

    public void Next() {
        int dx = 0; // 変位
        int dy = 0;

        switch (dir) {
            case Dir.Up: dy -= GameWindow.GRIDSIZE; break;
            case Dir.Down: dy += GameWindow.GRIDSIZE; break;
            case Dir.Left: dx -= GameWindow.GRIDSIZE; break;
            case Dir.Right: dx += GameWindow.GRIDSIZE; break;
            default: break;
        }
        
        _snake.Insert(0, new Block(_snake[0].X + dx, _snake[0].Y + dy));

        if (_addBlock) {
            _addBlock = false;
            return;
        }
        _snake.RemoveAt(_snake.Count - 1);
    }

    public void AddBlock() {
        _addBlock = true;
    }

    public void SetDir(Dir dir) {
        var flag1 = (this.dir == Dir.Up || this.dir == Dir.Down) && (dir == Dir.Up || dir == Dir.Down);
        var flag2 = (this.dir == Dir.Left || this.dir == Dir.Right) && (dir == Dir.Left || dir == Dir.Right);
        if (!(flag1 || flag2)) this.dir = dir;
    }

    public bool EatingFood(Block food) {
        return food.X == _snake[0].X && food.Y == _snake[0].Y;
    }

    public bool IsGameOver() {
        for (int i = 1; i < _snake.Count; i++) {
            if (_snake[0].X == _snake[i].X && _snake[0].Y == _snake[i].Y) return true;
        }
        return false;
    }
}

enum Dir {
    Up, Down, Left, Right
}
