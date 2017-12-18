import java.util.ArrayList;

public class TesterCoda {

	public static void main(String[] args) {
	
		
		/*Panel b0 = new Panel(false,0,true);
		Panel w1 = new Panel(true,1,true);
		
		Panel b1 = new Panel(false,1,false);
		Panel w4 = new Panel(true, 4, false);
		Panel b11 = new Panel(false,11,false);
		Panel w11 = new Panel(true,11, false);
		
		Panel b2 = new Panel(false,2,false);
		Panel w3 = new Panel(true,3,false);
		Panel b5 = new Panel(false,5,false);
		Panel w7 = new Panel(true,7,false);
		Panel w9 = new Panel(true,9, false);
		Panel w10 = new Panel(true,10,false);
		Panel w0 = new Panel(true,0,false);*/
		
		ArrayList<Panel> known = new ArrayList<Panel>();
		Panel b2 = new Panel(false,2,false);
		Panel w2 = new Panel(true,2,false);
		Panel b3 = new Panel(false,3,false);
		Panel b5 = new Panel(false,5,false);
		Panel w5 = new Panel(true,5,false);
		Panel w7 = new Panel(true,7,false);
		Panel w8 = new Panel(true,8,false);
		Panel w0 = new Panel(true,0,false);
		Panel b1 = new Panel(false,1,false);
		Panel w1 = new Panel(true,1,false);
		Panel b9 = new Panel(false,9,false);
		Panel w11 = new Panel(true,11,false);
		Panel b4 = new Panel(false,4,false);
		Panel b8 = new Panel(false,8,false);
		//Panel b1 = new Panel(false,1,false);
		Panel b10 = new Panel(false,10,false);
		Panel b6 = new Panel(false,6,false);
		known.add(b2);
		known.add(w2);
		known.add(b3);
		known.add(b5);
		known.add(w5);
		known.add(w7);
		known.add(w8);
		known.add(w0);
		known.add(b1);
		known.add(w1);
		known.add(b9);
		known.add(w11);
		known.add(b4);
		known.add(b8);
		known.add(b1);
		known.add(b10);
		known.add(b6);
		
		CodaGame poo = new CodaGame();
		
		ArrayList<Panel> userCode = new ArrayList<Panel>();
	/*	Panel w0 = new Panel(true,0,true);
		Panel b1 = new Panel(false,1,true);
		Panel w1 = new Panel(true,1,true);
		Panel b4 = new Panel(false,4,false);
		Panel b8 = new Panel(false,8,false);
		Panel b9 = new Panel(false,9,true);
		Panel b11 = new Panel(false,11,false);
		Panel w11 = new Panel(true,11,true);*/
		
		userCode.add(w0);
		userCode.add(b1);
		userCode.add(w1);
		userCode.add(b4);
		userCode.add(b8);
		userCode.add(b9);
		//userCode.add(b11);
		userCode.add(w11);
		/*userCode.add(b0);
		userCode.add(w1);
		userCode.add(b1);
		userCode.add(w4);
		userCode.add(b11);
		userCode.add(w11);*/
		
		
		/*known.add(b2);
		known.add(b0);
		known.add(w1);
		known.add(w3);
		known.add(b5);
		known.add(w7);
		known.add(w9);
		known.add(w10);
		known.add(w0);*/
 		
		
		
		
		Code foo = new Code(userCode);
		
		System.out.println(poo.ranges(foo,known,false));
	}
	
	//user hand: [white/F0, black/F1, white/F1, black/4, black/8, black/F9, black/11, white/F11]
	//[(start: -1 end: -1 range: 0), (start: -1 end: -1 range: 0), (start: -1 end: -1 range: 0), (start: 7 end: 0 range: -7), (start: 11 end: 7 range: -4), (start: -1 end: -1 range: 0), (start: 11 end: 11 range: 0), (start: -1 end: -1 range: 0)]
	//known: [black/F2, white/F2, black/F3, black/F5, white/5, white/7, white/8, white/F0, black/F1, white/F1, black/F9, white/F11, black/4, black/8, white/0, black/1, black/10, black/6]

}
