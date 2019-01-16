class GameObject2D {
    constructor(ctx, id, name, x, y, vx, vy) {
        this.ctx = ctx;
        this.id = id;
        this.name = name;
        this.x = x || 0;
        this.y = y || 0;
        this.vx = vx || 0;
        this.vy = vy || 0;
    }

    draw() {
        if(this.image) {
            this.ctx.drawImage(this.image, this.x, this.y, 80, 80 * this.image.height/this.image.width);
        } else {
            this.ctx.beginPath();
            this.ctx.arc(this.x, this.y, 20, 0, Math.PI * 2, true);
            this.ctx.closePath();
            this.ctx.fillStyle = 'blue';
            this.ctx.fill();
        }
    }
}