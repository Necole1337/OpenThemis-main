public class AutoBlockA extends Check {
    public AutoBlockA(@NotNull TRPlayer player) {
        super("AutoBlockA", player);
    }

    @Override
    public void _onTick() {
        if (player.fabricPlayer.isBlocking() && !player.lastSwing && player.currentSwing) {
            flag("impossible hit.");
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.autoBlockAAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getCombatCheck().isToggled();
    }
}
