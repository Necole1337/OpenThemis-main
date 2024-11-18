public abstract class Check {
    protected final @NotNull TRPlayer player;
    public String checkName;
    public int violations;

    public Check(String checkName, @NotNull TRPlayer player) {
        this.checkName = checkName;
        this.player = player;
    }

    public abstract int getAlertBuffer();
    public abstract boolean isDisabled();

    protected void flag() {
        if (player.manager.disableTick > 0) return;
        if (isDisabled()) return;
        if (!Anticheat.getCheckForSelf().isToggled() && player.equals(TRSelf.getInstance())) return;
        violations++;
        if (!Anticheat.getNoAlertBuffer().isToggled())
            if (violations % getAlertBuffer() != 0) return;
        LogUtils.alert(player.fabricPlayer.getDisplayName().getFormattedText(), checkName, String.format("(VL:%s)", violations));
    }

    protected void flag(String extraMsg) {
        if (player.manager.disableTick > 0) return;
        if (isDisabled()) return;
        if (!Anticheat.getCheckForSelf().isToggled() && player.equals(TRSelf.getInstance())) return;
        violations++;
        if (!Anticheat.getNoAlertBuffer().isToggled())
            if (violations % getAlertBuffer() != 0) return;
        LogUtils.alert(player.fabricPlayer.getDisplayName().getFormattedText(), checkName, String.format("(VL:%s) %s%s", violations, ChatFormatting.GRAY, extraMsg));
    }

    protected void moduleMsg(String msg) {
        LogUtils.prefix(checkName, msg);
    }

    protected static void customMsg(String msg) {
        LogUtils.custom(msg);
    }

    public void _onTick() {}
    public void _onTeleport() {}
    public void _onJump() {}
    public void _onGameTypeChange() {}
    public void _onPlaceBlock() {}
}
