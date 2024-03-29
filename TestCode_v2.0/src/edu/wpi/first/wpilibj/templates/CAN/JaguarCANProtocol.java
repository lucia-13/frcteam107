
package edu.wpi.first.wpilibj.templates.CAN;

public interface JaguarCANProtocol {

    //*****************************************************************************
    //
    // The masks of the fields that are used in the message identifier.
    //
    //*****************************************************************************
    public static final int CAN_MSGID_FULL_M       = 0x1fffffff;
    public static final int CAN_MSGID_DEVNO_M      = 0x0000003f;
    public static final int CAN_MSGID_API_M        = 0x0000ffc0;
    public static final int CAN_MSGID_MFR_M        = 0x00ff0000;
    public static final int CAN_MSGID_DTYPE_M      = 0x1f000000;
    public static final int CAN_MSGID_DEVNO_S      = 0;
    public static final int CAN_MSGID_API_S        = 6;
    public static final int CAN_MSGID_MFR_S        = 16;
    public static final int CAN_MSGID_DTYPE_S      = 24;

    //*****************************************************************************
    //
    // The Reserved device number values in the Message Id.
    //
    //*****************************************************************************
    public static final int CAN_MSGID_DEVNO_BCAST  = 0x00000000;

    //*****************************************************************************
    //
    // The Reserved system control API numbers in the Message Id.
    //
    //*****************************************************************************
    public static final int CAN_MSGID_API_SYSHALT  = 0x00000000;
    public static final int CAN_MSGID_API_SYSRST   = 0x00000040;
    public static final int CAN_MSGID_API_DEVASSIGN= 0x00000080;
    public static final int CAN_MSGID_API_DEVQUERY = 0x000000c0;
    public static final int CAN_MSGID_API_HEARTBEAT= 0x00000140;
    public static final int CAN_MSGID_API_SYNC     = 0x00000180;
    public static final int CAN_MSGID_API_UPDATE   = 0x000001c0;
    public static final int CAN_MSGID_API_FIRMVER  = 0x00000200;
    public static final int CAN_MSGID_API_ENUMERATE= 0x00000240;
    public static final int CAN_MSGID_API_SYSRESUME= 0x00000280;

    //*****************************************************************************
    //
    // The 32 bit values associated with the CAN_MSGID_API_STATUS request.
    //
    //*****************************************************************************
    public static final int CAN_STATUS_CODE_M      = 0x0000ffff;
    public static final int CAN_STATUS_MFG_M       = 0x00ff0000;
    public static final int CAN_STATUS_DTYPE_M     = 0x1f000000;
    public static final int CAN_STATUS_CODE_S      = 0;
    public static final int CAN_STATUS_MFG_S       = 16;
    public static final int CAN_STATUS_DTYPE_S     = 24;

    //*****************************************************************************
    //
    // The Reserved manufacturer identifiers in the Message Id.
    //
    //*****************************************************************************
    public static final int CAN_MSGID_MFR_NI       = 0x00010000;
    public static final int CAN_MSGID_MFR_LM       = 0x00020000;
    public static final int CAN_MSGID_MFR_DEKA     = 0x00030000;

    //*****************************************************************************
    //
    // The Reserved device type identifiers in the Message Id.
    //
    //*****************************************************************************
    public static final int CAN_MSGID_DTYPE_BCAST  = 0x00000000;
    public static final int CAN_MSGID_DTYPE_ROBOT  = 0x01000000;
    public static final int CAN_MSGID_DTYPE_MOTOR  = 0x02000000;
    public static final int CAN_MSGID_DTYPE_RELAY  = 0x03000000;
    public static final int CAN_MSGID_DTYPE_GYRO   = 0x04000000;
    public static final int CAN_MSGID_DTYPE_ACCEL  = 0x05000000;
    public static final int CAN_MSGID_DTYPE_USONIC = 0x06000000;
    public static final int CAN_MSGID_DTYPE_GEART  = 0x07000000;
    public static final int CAN_MSGID_DTYPE_UPDATE = 0x1f000000;

    //*****************************************************************************
    //
    // LM Motor Control API Classes API Class and ID masks.
    //
    //*****************************************************************************
    public static final int CAN_MSGID_API_CLASS_M  = 0x0000fc00;
    public static final int CAN_MSGID_API_ID_M     = 0x000003c0;

    //*****************************************************************************
    //
    // LM Motor Control API Classes in the Message Id for non-broadcast.
    // These are the upper 6 bits of the API field, the lower 4 bits determine
    // the APIId.
    //
    //*****************************************************************************
    public static final int CAN_API_MC_VOLTAGE     = 0x00000000;
    public static final int CAN_API_MC_SPD         = 0x00000400;
    public static final int CAN_API_MC_POS         = 0x00000c00;
    public static final int CAN_API_MC_ICTRL       = 0x00001000;
    public static final int CAN_API_MC_STATUS      = 0x00001400;
    public static final int CAN_API_MC_CFG         = 0x00001c00;
    public static final int CAN_API_MC_ACK         = 0x00002000;

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Control Voltage API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_VOLT            = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_VOLTAGE);
    public static final int LM_API_VOLT_EN         = (LM_API_VOLT | (0 << CAN_MSGID_API_S));
    public static final int LM_API_VOLT_DIS        = (LM_API_VOLT | (1 << CAN_MSGID_API_S));
    public static final int LM_API_VOLT_SET        = (LM_API_VOLT | (2 << CAN_MSGID_API_S));
    public static final int LM_API_VOLT_SET_RAMP   = (LM_API_VOLT | (3 << CAN_MSGID_API_S));
    //##### FIRST BEGIN #####
    public static final int LM_API_VOLT_T_EN       = (LM_API_VOLT | (4 << CAN_MSGID_API_S));
    public static final int LM_API_VOLT_T_SET      = (LM_API_VOLT | (5 << CAN_MSGID_API_S));
    //##### FIRST END #####

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Control API definitions for
    // LM_API_VOLT_SET_RAMP.
    //
    //*****************************************************************************
    public static final int LM_API_VOLT_RAMP_DIS   = 0;

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Control API definitions for
    // CAN_MSGID_API_SYNC.
    //
    //*****************************************************************************
    public static final int LM_API_SYNC_PEND_NOW   = 0;

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Speed Control API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_SPD             = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_SPD);
    public static final int LM_API_SPD_EN          = (LM_API_SPD | (0 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_DIS         = (LM_API_SPD | (1 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_SET         = (LM_API_SPD | (2 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_PC          = (LM_API_SPD | (3 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_IC          = (LM_API_SPD | (4 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_DC          = (LM_API_SPD | (5 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_REF         = (LM_API_SPD | (6 << CAN_MSGID_API_S));
    //##### FIRST BEGIN #####
    public static final int LM_API_SPD_T_EN        = (LM_API_SPD | (7 << CAN_MSGID_API_S));
    public static final int LM_API_SPD_T_SET       = (LM_API_SPD | (8 << CAN_MSGID_API_S));
    //##### FIRST END #####

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Position Control API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_POS             = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_POS);
    public static final int LM_API_POS_EN          = (LM_API_POS | (0 << CAN_MSGID_API_S));
    public static final int LM_API_POS_DIS         = (LM_API_POS | (1 << CAN_MSGID_API_S));
    public static final int LM_API_POS_SET         = (LM_API_POS | (2 << CAN_MSGID_API_S));
    public static final int LM_API_POS_PC          = (LM_API_POS | (3 << CAN_MSGID_API_S));
    public static final int LM_API_POS_IC          = (LM_API_POS | (4 << CAN_MSGID_API_S));
    public static final int LM_API_POS_DC          = (LM_API_POS | (5 << CAN_MSGID_API_S));
    public static final int LM_API_POS_REF         = (LM_API_POS | (6 << CAN_MSGID_API_S));
    //##### FIRST BEGIN #####
    public static final int LM_API_POS_T_EN        = (LM_API_POS | (7 << CAN_MSGID_API_S));
    public static final int LM_API_POS_T_SET       = (LM_API_POS | (8 << CAN_MSGID_API_S));
    //##### FIRST END #####

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Current Control API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_ICTRL           = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_ICTRL);
    public static final int LM_API_ICTRL_EN        = (LM_API_ICTRL | (0 << CAN_MSGID_API_S));
    public static final int LM_API_ICTRL_DIS       = (LM_API_ICTRL | (1 << CAN_MSGID_API_S));
    public static final int LM_API_ICTRL_SET       = (LM_API_ICTRL | (2 << CAN_MSGID_API_S));
    public static final int LM_API_ICTRL_PC        = (LM_API_ICTRL | (3 << CAN_MSGID_API_S));
    public static final int LM_API_ICTRL_IC        = (LM_API_ICTRL | (4 << CAN_MSGID_API_S));
    public static final int LM_API_ICTRL_DC        = (LM_API_ICTRL | (5 << CAN_MSGID_API_S));
    //##### FIRST BEGIN #####
    public static final int LM_API_ICTRL_T_EN      = (LM_API_ICTRL | (6 << CAN_MSGID_API_S));
    public static final int LM_API_ICTRL_T_SET     = (LM_API_ICTRL | (7 << CAN_MSGID_API_S));
    //##### FIRST END #####

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Firmware Update API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_UPD             = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_UPDATE);
    public static final int LM_API_UPD_PING        = (LM_API_UPD | (0 << CAN_MSGID_API_S));
    public static final int LM_API_UPD_DOWNLOAD    = (LM_API_UPD | (1 << CAN_MSGID_API_S));
    public static final int LM_API_UPD_SEND_DATA   = (LM_API_UPD | (2 << CAN_MSGID_API_S));
    public static final int LM_API_UPD_RESET       = (LM_API_UPD | (3 << CAN_MSGID_API_S));
    public static final int LM_API_UPD_ACK         = (LM_API_UPD | (4 << CAN_MSGID_API_S));
    public static final int LM_API_HWVER           = (LM_API_UPD | (5 << CAN_MSGID_API_S));
    //##### FIRST BEGIN #####
    public static final int LM_API_UNTRUST_EN      = (LM_API_UPD | (11 << CAN_MSGID_API_S));
    public static final int LM_API_TRUST_EN        = (LM_API_UPD | (12 << CAN_MSGID_API_S));
    public static final int LM_API_TRUST_HEARTBEAT = (LM_API_UPD | (13 << CAN_MSGID_API_S));
    //##### FIRST END #####

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Status API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_STATUS          = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_STATUS);
    public static final int LM_API_STATUS_VOLTOUT  = (LM_API_STATUS | (0 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_VOLTBUS  = (LM_API_STATUS | (1 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_CURRENT  = (LM_API_STATUS | (2 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_TEMP     = (LM_API_STATUS | (3 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_POS      = (LM_API_STATUS | (4 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_SPD      = (LM_API_STATUS | (5 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_LIMIT    = (LM_API_STATUS | (6 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_FAULT    = (LM_API_STATUS | (7 << CAN_MSGID_API_S));
    public static final int LM_API_STATUS_POWER    = (LM_API_STATUS | (8 << CAN_MSGID_API_S));

    //*****************************************************************************
    //
    // These definitions are used with the byte that is returned from
    // the status request for LM_API_STATUS_LIMIT.
    //
    //*****************************************************************************
    public static final int LM_STATUS_LIMIT_FWD    = 0x01;
    public static final int LM_STATUS_LIMIT_REV    = 0x02;

    //*****************************************************************************
    //
    // LM Motor Control status codes returned due to the CAN_STATUS_CODE_M field.
    //
    //*****************************************************************************
    public static final int LM_STATUS_FAULT_ILIMIT = 0x01;
    public static final int LM_STATUS_FAULT_TLIMIT = 0x02;
    public static final int LM_STATUS_FAULT_VLIMIT = 0x04;

    //*****************************************************************************
    //
    // The Luminary Micro Motor Class Configuration API definitions.
    //
    //*****************************************************************************
    public static final int LM_API_CFG             = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_CFG);
    public static final int LM_API_CFG_NUM_BRUSHES = (LM_API_CFG | (0 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_ENC_LINES   = (LM_API_CFG | (1 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_POT_TURNS   = (LM_API_CFG | (2 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_BRAKE_COAST = (LM_API_CFG | (3 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_LIMIT_MODE  = (LM_API_CFG | (4 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_LIMIT_FWD   = (LM_API_CFG | (5 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_LIMIT_REV   = (LM_API_CFG | (6 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_MAX_VOUT    = (LM_API_CFG | (7 << CAN_MSGID_API_S));
    public static final int LM_API_CFG_FAULT_TIME  = (LM_API_CFG | (8 << CAN_MSGID_API_S));

    //*****************************************************************************
    //
    // The Luminary Micro ACK API definition.
    //
    //*****************************************************************************
    public static final int LM_API_ACK             = (CAN_MSGID_MFR_LM | CAN_MSGID_DTYPE_MOTOR |
                                     CAN_API_MC_ACK);

    //*****************************************************************************
    //
    // The 8 bit values that can be returned by a call to LM_API_STATUS_HWVER.
    //
    //*****************************************************************************
    public static final int LM_HWVER_UNKNOWN       = 0x00;
    public static final int LM_HWVER_JAG_1_0       = 0x01;
    public static final int LM_HWVER_JAG_2_0       = 0x02;

}
