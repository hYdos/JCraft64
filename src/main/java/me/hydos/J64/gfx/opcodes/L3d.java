package me.hydos.J64.gfx.opcodes;

import static me.hydos.J64.emu.util.debug.Debug.DEBUG_MICROCODE;
import static me.hydos.J64.gfx.Gbi.*;

import me.hydos.J64.gfx.rdp.Gdp;
import me.hydos.J64.gfx.rsp.GBIFunc;
import me.hydos.J64.gfx.rsp.Gsp;

public class L3d extends F3d {
    
    public static final int L3D_LINE3D = 0xB5;

    
    public static GBIFunc L3D_Line3D = new GBIFunc() {
        @Override
		public void exec(int w0, int w1) {
            int wd = w1&SR_MASK_8;
            if (wd == 0)
                gsp.gSPLine3D(((w1>>16)&SR_MASK_8) / 10, ((w1>>8)&SR_MASK_8) / 10, (w1>>24)&SR_MASK_8);
            else
                gsp.gSPLineW3D(((w1>>16)&SR_MASK_8) / 10, ((w1>>8)&SR_MASK_8) / 10, wd, (w1>>24)&SR_MASK_8);
        }
    };
    
    public static void init(Gsp rsp, Gdp rdp) {
        gsp = rsp;
        gdp = rdp;
        F3d.GBI_InitFlags();
        G_SPNOOP = F3d.F3D_SPNOOP;
        G_MTX = F3d.F3D_MTX;
        G_RESERVED0 = F3d.F3D_RESERVED0;
        G_MOVEMEM = F3d.F3D_MOVEMEM;
        G_VTX = F3d.F3D_VTX;
        G_RESERVED1 = F3d.F3D_RESERVED1;
        G_DL = F3d.F3D_DL;
        G_RESERVED2 = F3d.F3D_RESERVED2;
        G_RESERVED3 = F3d.F3D_RESERVED3;
        G_SPRITE2D_BASE = F3d.F3D_SPRITE2D_BASE;
        G_CULLDL = F3d.F3D_CULLDL;
        G_POPMTX = F3d.F3D_POPMTX;
        G_MOVEWORD = F3d.F3D_MOVEWORD;
        G_TEXTURE = F3d.F3D_TEXTURE;
        G_SETOTHERMODE_H = F3d.F3D_SETOTHERMODE_H;
        G_SETOTHERMODE_L = F3d.F3D_SETOTHERMODE_L;
        G_ENDDL = F3d.F3D_ENDDL;
        G_SETGEOMETRYMODE = F3d.F3D_SETGEOMETRYMODE;
        G_CLEARGEOMETRYMODE = F3d.F3D_CLEARGEOMETRYMODE;
        G_LINE3D = L3D_LINE3D;
        G_RDPHALF_1 = F3d.F3D_RDPHALF_1;
        G_RDPHALF_2 =  F3d.F3D_RDPHALF_2;
        G_RDPHALF_CONT = F3d.F3D_RDPHALF_CONT;
        
        gsp.pcStackSize = 10;
        
        gsp.setGBI(G_SPNOOP, F3d.F3D_SPNoOp);
        gsp.setGBI(G_MTX, F3d.F3D_Mtx);
        gsp.setGBI(G_RESERVED0, F3d.F3D_Reserved0);
        gsp.setGBI(G_MOVEMEM, F3d.F3D_MoveMem);
        gsp.setGBI(G_VTX, F3d.F3D_Vtx);
        gsp.setGBI(G_RESERVED1, F3d.F3D_Reserved1);
        gsp.setGBI(G_DL, F3d.F3D_DList);
        gsp.setGBI(G_RESERVED2, F3d.F3D_Reserved2);
        gsp.setGBI(G_RESERVED3, F3d.F3D_Reserved3);
        gsp.setGBI(G_SPRITE2D_BASE, F3d.F3D_Sprite2D_Base);
        
        gsp.setGBI(G_CULLDL, F3d.F3D_CullDL);
        gsp.setGBI(G_POPMTX, F3d.F3D_PopMtx);
        gsp.setGBI(G_MOVEWORD, F3d.F3D_MoveWord);
        gsp.setGBI(G_TEXTURE, F3d.F3D_Texture);
        gsp.setGBI(G_SETOTHERMODE_H, F3d.F3D_SetOtherMode_H);
        gsp.setGBI(G_SETOTHERMODE_L, F3d.F3D_SetOtherMode_L);
        gsp.setGBI(G_ENDDL, F3d.F3D_EndDL);
        gsp.setGBI(G_SETGEOMETRYMODE, F3d.F3D_SetGeometryMode);
        gsp.setGBI(G_CLEARGEOMETRYMODE, F3d.F3D_ClearGeometryMode);
        gsp.setGBI(G_LINE3D, L3D_Line3D);
        gsp.setGBI(G_RDPHALF_1, F3d.F3D_RDPHalf_1);
        gsp.setGBI(G_RDPHALF_2, F3d.F3D_RDPHalf_2);
        gsp.setGBI(G_RDPHALF_CONT, F3d.F3D_RDPHalf_Cont);
        
        if (DEBUG_MICROCODE) System.out.println("Initialized L3D opcodes");
    }
    
}
