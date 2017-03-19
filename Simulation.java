//This is a program to simulate the
//diffusion of an ideal gas.However
//it can be extended further by tweaking the value
//of the coefficient of restitution.


//Date :: 5/10/2015
//Author :: ARIF AHMED

package edu.princeton.cs.algs4;

class Simulation
{
  int show_time = 2;
  class Particle
   {
     //All the parameters of the particle such as radius,mass,velocity,etc.
     double x,y, v_x , v_y;
     double radius,mass,size,cosine,sine;
     int side,k;//'k' denotes the mass multiplier, 'side' denotes whichever side the particle is present.
     public Particle(int k)
     {
         //Initial dimensions of every particle.
         //A random direction is given to every particle,
         //but same speed.The starting coordinates of every particle
         //are completely random.
         this.x = StdRandom.uniform() * 0.46;
         this.y = StdRandom.uniform();
         this.cosine = StdRandom.uniform();
         this.sine = 1 - this.cosine;
         this.v_x = 0.007*this.cosine;
         this.v_y = 0.007*this.sine;
         this.radius = 0.005 * k;

         this.mass = Math.pow(this.radius,2)*10000;
         //'this.size' is the size of the gap in the wall.
         this.size = 0.4;

         this.k = k;
         if(x <= 0.5)//Check if particle is present on the left or the right side.
             this.side = 0;
         else
             this.side = 1;
         this.mass = (double)Math.pow(this.radius,2) * 10000;
     }
     void draw()
     {
         StdDraw.filledCircle(this.x,this.y,this.radius);//Particles are drawn as perfect circles.
         if(this.k == 1)
           StdDraw.setPenColor(255,0,0);
         else if(this.k == 2)
           StdDraw.setPenColor(0,255,0);
     }
     void update()
     {
         if(this.k == 1)
           StdDraw.setPenColor(255,0,0);
         else if(this.k == 2)
           StdDraw.setPenColor(0,255,0);
         this.x += this.v_x;//Update position.
         this.y += this.v_y;

         if(( this.y > (0.5-this.size+this.radius) )&& (this.y < (0.5-this.radius)))//This is where particles cross.
         {
             if((this.x+this.radius) > 0.501 && this.side == 0)//This is the gap.
               this.side = 1;                                 //If particle has crossed the gap then it belongs on the other side now.
             if((this.x-this.radius) < 0.503 && this.side == 1)//This is the gap.
               this.side = 0;
         }
         else//If not in the gap , bounce back.
           {
               if( (this.x+this.radius) >= 0.501 && this.side == 0)
                 {
                    this.v_x = -1*this.v_x;
                    while((this.x+this.radius) >= 0.501)
                       this.x -= 0.001;
                 }
                if( (this.x-this.radius) <= 0.503 && this.side == 1)
                 {
                    this.v_x = -1*this.v_x;
                    while((this.x-this.radius) <= 0.503)
                       this.x += 0.001;
                 }
           }
         //The next four 'if' blocks are for particle - wall collisions.
         if( (this.x+this.radius) >= 1)
         {
             this.v_x = -1*this.v_x;
             while((this.x+this.radius) >= 1)
                 this.x -= 0.001;
         }
         if(this.x <= this.radius)
         {
             this.v_x = -1*this.v_x;
             while(this.x <= this.radius)
                 this.x += 0.001;
         }
         if( (this.y+this.radius) >= 1)
         {
             this.v_y = -1*this.v_y;
             while((this.y+this.radius) >= 1)
                 this.y -= 0.001;
         }
         if(this.y <= this.radius)
         {
             this.v_y = -1*this.v_y;
             while((this.y <= this.radius))
                 this.y += 0.001;
         }
       }
   }
    public void simulate()
     {
       //Main function to run the simulation.
       int N = 200;//Number of particles.
       double e = 1;//Coefficient of restitution.
       double size = 0.4;//Size of the gap.
       Particle A[] = new Particle[N];//Array of particles.

       for(int i = 0;i < N;i++) //Create an array of particles with random velocities and positions.
          A[i] = new Particle(1);

       int left = 0;//Counts hwo many particles
       int right = 0;//are on the left and right sides respectively.
       int pressed = 0;//Signals if a key has been pressed.Signal for beginning the simulation.
           while(pressed == 0)
            {
                 if(StdDraw.hasNextKeyTyped() == true)
                     pressed = 1;
                 StdDraw.clear();
                 left = 0;
                 right = 0;
                 StdDraw.setPenColor(0,0,0);
                 for(int i = 0;i < N;i++)
                 {
                    if(A[i].side == 0)//Checking if the particles belong on the left or right side.
                      left++;
                    else
                      right++;
                 }

                  StdDraw.setPenColor(0,0,0);
                  StdDraw.text(0.05,0.95,String.valueOf(left));//Displays number of particles on the left and right sides.
                  StdDraw.text(0.8,0.95,String.valueOf(right));
                  StdDraw.setPenColor(255,0,0);
                  for(int i = 0;i < N;i++)
                      A[i].draw();

                  //This block of code draws the wall.

                  StdDraw.setPenColor(70,12,123);
                  StdDraw.line(0.5,0,0.5,0.5 - size);
                  StdDraw.line(0.501,0,0.501,0.5 - size);
                  StdDraw.line(0.502,0,0.502,0.5 - size);
                  StdDraw.line(0.503,0,0.503,0.5 - size);
                  StdDraw.line(0.5,0.5,0.5,1);
                  StdDraw.line(0.501,0.5,0.501,1);
                  StdDraw.line(0.502,0.5,0.502,1);
                  StdDraw.line(0.503,0.5,0.503,1);
                  StdDraw.text(0.5,0.5,"Press any key to start simulation.");
                  StdDraw.show(10);
           }
           while(true)
              {

                 StdDraw.clear();
                 StdDraw.setPenColor(70,12,123);
                 StdDraw.line(0.5,0,0.5,0.5 - size);
                 StdDraw.line(0.501,0,0.501,0.5 - size);
                 StdDraw.line(0.502,0,0.502,0.5 - size);
                 StdDraw.line(0.503,0,0.503,0.5 - size);
                 StdDraw.line(0.5,0.5,0.5,1);
                 StdDraw.line(0.501,0.5,0.501,1);
                 StdDraw.line(0.502,0.5,0.502,1);
                 StdDraw.line(0.503,0.5,0.503,1);
                 left = 0;
                 right = 0;

                 for(int i = 0;i < N;i++)
                 {
                    if(A[i].side == 0)
                      left++;
                    else
                      right++;
                 }

                  StdDraw.setPenColor(0,0,0);
                  StdDraw.text(0.02,0.9,String.valueOf(left));
                  StdDraw.text(0.8,0.9,String.valueOf(right));
                  StdDraw.setPenColor(255,0,0);

                  for(int i = 0;i < N;i++)
                   {
                     //Check collisions with other particles.
                      for(int j = 0;j < N;j++)
                        {
                            if( (dist(A[i],A[j]) <= (A[i].radius + A[j].radius + 0.3*A[i].radius)) && i != j)
                               {
                                 double u_i_x = A[i].v_x;
                                 double u_j_x = A[j].v_x;
                                 double u_i_y = A[i].v_y;
                                 double u_j_y = A[j].v_y;

                                 if( (u_i_x == u_j_x) && (u_i_y == u_j_y) )
                                    {
                                      while(dist(A[i],A[j]) <= (A[i].radius + A[j].radius))
                                        {
                                           A[i].x -= u_i_x;
                                           A[j].x += u_j_x;
                                           A[i].y -= u_i_y;
                                           A[j].y += u_j_y;
                                        }
                                    }
                                  //Final velocities are judged according to the laws of collisions.
                                  A[i].v_x = (A[i].mass*u_i_x + A[j].mass*u_j_x + A[j].mass*e*(u_j_x - u_i_x))/(A[i].mass + A[j].mass);
                                  A[j].v_x = (A[i].mass*u_i_x*(1 + e) + u_j_x*(A[j].mass - A[i].mass*e))/(A[i].mass + A[j].mass);

                                  A[i].v_y = (A[i].mass*u_i_y + A[j].mass*u_j_y + A[j].mass*e*(u_j_y - u_i_y))/(A[i].mass + A[j].mass);
                                  A[j].v_y = (A[i].mass*u_i_y*(1 + e) + u_j_y*(A[j].mass - A[i].mass*e))/(A[i].mass + A[j].mass);
                                }
                         }
                       A[i].draw();
                       A[i].update();
                   }
           StdDraw.show(show_time);//Show for certain time.
       }
    }

    public double dist(Particle A,Particle B)//Helper function to compute the distance between any two particles.
    {
        return (double)Math.sqrt( (A.x - B.x)*(A.x - B.x) + (A.y - B.y)*(A.y - B.y) );
    }
    public static void main(String[]args)
    {
        Simulation obj = new Simulation();
        obj.simulate();//Create a Simulation object and call its main method.
    }
}
