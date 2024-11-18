public class GroundSpoofB extends Check {
    public GroundSpoofB(@NotNull TRPlayer player) {
        super("GroundSpoofB", player);
    }

    @Override
    public void _onTick() {
        final WorldClient level = LevelUtils.getClientLevel();
        if (level == null) return;

        if (!player.currentOnGround && Math.floor(player.currentPos.y()) == player.currentPos.y()) {  // check if it's *OnGround*
            if (check(level, new BlockPos(player.fabricPlayer).down())) {
                flag("spoof onGround=false");
                setback();
            }
        }
    }

    public void setback() {
        player.currentOnGround = true;
    }

    public static boolean check(@NotNull WorldClient level, @NotNull BlockPos groundPos) {
        if (!BlockUtils.isFullBlock(level.getBlockState(groundPos)))
            return false;

        short count = 0;
        final List<BlockPos> blocks = new ArrayList<>();
        blocks.add(groundPos.east());
        blocks.add(groundPos.east().north());
        blocks.add(groundPos.west());
        blocks.add(groundPos.west().south());
        blocks.add(groundPos.north());
        blocks.add(groundPos.north().west());
        blocks.add(groundPos.south());
        blocks.add(groundPos.south().east());

        for (BlockPos blockPos : blocks) {
            if (BlockUtils.isFullBlock(level.getBlockState(blockPos))) {
                count++;
            }
        }

        return count >= 8;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.groundSpoofBAlertBuffer;
    }
}
