ppublic class SpeedB extends Check {
    public SpeedB(@NotNull TRPlayer player) {
        super("SpeedB", player);
    }

    @Override
    public void _onTick() {
        if (player.fabricPlayer.isSprinting() && player.fabricPlayer.getFoodStats().getFoodLevel() <= 6) {
            flag();
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.speedBAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }
}
