public class FlyB extends Check {
    public static final List<Block> IGNORED_BLOCKS = new ArrayList<>();
    public FlyB(@NotNull TRPlayer player) {
        super("FlyB", player);
        IGNORED_BLOCKS.add(Blocks.web);
        IGNORED_BLOCKS.add(Blocks.water);
        IGNORED_BLOCKS.add(Blocks.lava);
        IGNORED_BLOCKS.add(Blocks.slime_block);
        IGNORED_BLOCKS.add(Blocks.soul_sand);
    }

    @Override
    public void _onTick() {
        if (player.currentOnGround
//                || player.fabricPlayer.hurtTime > 0
                || IGNORED_BLOCKS.stream().anyMatch(block -> LevelUtils.getClientLevel().getBlockState(new BlockPos(player.fabricPlayer)).getBlock().equals(block))) return;

        if (PlayerMove.isInvalidMotion(player.currentMotion)) return;


        List<Vec3> posDiff = PlayerMove.getPosHistoryDiff(player.posHistory);
        int repeatFromTick = 0;
        for (Vec3 diff : posDiff) {
            if (diff.y() == posDiff.get(0).y()) {
                repeatFromTick++;
            }
        }

        if (repeatFromTick >= AdvancedConfig.flyBMinRepeatTicks) {
            flag(String.format("Repeat Y-diff from %s ticks: %.2f", repeatFromTick, posDiff.get(0).y()));
        }
    }

    @Override
    public int getAlertBuffer() {
        return AdvancedConfig.flyBAlertBuffer;
    }

    @Override
    public boolean isDisabled() {
        return !Anticheat.getMovementCheck().isToggled();
    }
}
