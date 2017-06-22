
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

  public void testEmptyMessages() throws Exception {
    hookDoesNotModify("\n# on branch master\ndiff --git a/src b/src\n"
        + "new file mode 100644\nindex 0000000..c78b7f0\n");
  public void testChangeIdAlreadySet() throws Exception {
    hookDoesNotModify("a\n" + //
        "\n" + //
        "Change-Id: Iaeac9b4149291060228ef0154db2985a31111335\n");
    hookDoesNotModify("fix: this thing\n" + //
        "\n" + //
        "Change-Id: I388bdaf52ed05b55e62a22d0a20d2c1ae0d33e7e\n");
    hookDoesNotModify("fix-a-widget: this thing\n" + //
        "\n" + //
        "Change-Id: Id3bc5359d768a6400450283e12bdfb6cd135ea4b\n");
    hookDoesNotModify("FIX: this thing\n" + //
        "\n" + //
        "Change-Id: I1b55098b5a2cce0b3f3da783dda50d5f79f873fa\n");
    hookDoesNotModify("Fix-A-Widget: this thing\n" + //
        "\n" + //
        "Change-Id: I4f4e2e1e8568ddc1509baecb8c1270a1fb4b6da7\n");
  public void testTimeAltersId() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n",//
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I3251906b99dda598a58a6346d8126237ee1ea800\n",//
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I69adf9208d828f41a3d7e41afbca63aff37c0c5c\n",//
  public void testFirstParentAltersId() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n",//
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I51e86482bde7f92028541aaf724d3a3f996e7ea2\n",//
  public void testDirCacheAltersId() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n",//
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: If56597ea9759f23b070677ea6f064c60c38da631\n",//
  public void testSingleLineMessages() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n",//
    assertEquals("fix: this thing\n" + //
        "\n" + //
        "Change-Id: I0f13d0e6c739ca3ae399a05a93792e80feb97f37\n",//
    assertEquals("fix-a-widget: this thing\n" + //
        "\n" + //
        "Change-Id: I1a1a0c751e4273d532e4046a501a612b9b8a775e\n",//
    assertEquals("FIX: this thing\n" + //
        "\n" + //
        "Change-Id: If816d944c57d3893b60cf10c65931fead1290d97\n",//
    assertEquals("Fix-A-Widget: this thing\n" + //
        "\n" + //
        "Change-Id: I3e18d00cbda2ba1f73aeb63ed8c7d57d7fd16c76\n",//
  public void testMultiLineMessagesWithoutFooter() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "b\n" + //
        "\n" + //
        "Change-Id: Id0b4f42d3d6fc1569595c9b97cb665e738486f5d\n",//
        call("a\n" + "\n" + "b\n"));

    assertEquals("a\n" + //
        "\n" + //
        "b\nc\nd\ne\n" + //
        "\n" + //
        "Change-Id: I7d237b20058a0f46cc3f5fabc4a0476877289d75\n",//
        call("a\n" + "\n" + "b\nc\nd\ne\n"));

    assertEquals("a\n" + //
        "\n" + //
        "b\nc\nd\ne\n" + //
        "\n" + //
        "f\ng\nh\n" + //
        "\n" + //
        "Change-Id: I382e662f47bf164d6878b7fe61637873ab7fa4e8\n",//
        call("a\n" + "\n" + "b\nc\nd\ne\n" + "\n" + "f\ng\nh\n"));
  public void testSingleLineMessagesWithSignedOffBy() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n" + //
        SOB1,//
        call("a\n" + "\n" + SOB1));

    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n" + //
        SOB1 + //
        SOB2,//
        call("a\n" + "\n" + SOB1 + SOB2));
  public void testMultiLineMessagesWithSignedOffBy() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "b\nc\nd\ne\n" + //
        "\n" + //
        "f\ng\nh\n" + //
        "\n" + //
        "Change-Id: I382e662f47bf164d6878b7fe61637873ab7fa4e8\n" + //
        SOB1,//
        call("a\n" + "\n" + "b\nc\nd\ne\n" + "\n" + "f\ng\nh\n" + "\n" + SOB1));

    assertEquals("a\n" + //
        "\n" + //
        "b\nc\nd\ne\n" + //
        "\n" + //
        "f\ng\nh\n" + //
        "\n" + //
        "Change-Id: I382e662f47bf164d6878b7fe61637873ab7fa4e8\n" + //
        SOB1 + //
        SOB2,//
        call("a\n" + //
            "\n" + //
            "b\nc\nd\ne\n" + //
            "\n" + //
            "f\ng\nh\n" + //
            "\n" + //
            SOB1 + //
            SOB2));

    assertEquals("a\n" + //
        "\n" + //
        "b: not a footer\nc\nd\ne\n" + //
        "\n" + //
        "f\ng\nh\n" + //
        "\n" + //
        "Change-Id: I8869aabd44b3017cd55d2d7e0d546a03e3931ee2\n" + //
        SOB1 + //
        SOB2,//
        call("a\n" + //
            "\n" + //
            "b: not a footer\nc\nd\ne\n" + //
            "\n" + //
            "f\ng\nh\n" + //
            "\n" + //
            SOB1 + //
            SOB2));
  public void testNoteInMiddle() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "NOTE: This\n" + //
        "does not fix it.\n" + //
        "\n" + //
        "Change-Id: I988a127969a6ee5e58db546aab74fc46e66847f8\n", //
        call("a\n" + //
            "\n" + //
            "NOTE: This\n" + //
            "does not fix it.\n"));
  public void testKernelStyleFooter() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I1bd787f9e7590a2ac82b02c404c955ffb21877c4\n" + //
        SOB1 + //
        "[ja: Fixed\n" + //
        "     the indentation]\n" + //
        SOB2, //
        call("a\n" + //
            "\n" + //
            SOB1 + //
            "[ja: Fixed\n" + //
            "     the indentation]\n" + //
            SOB2));
  public void testChangeIdAfterBugOrIssue() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Bug: 42\n" + //
        "Change-Id: I8c0321227c4324e670b9ae8cf40eccc87af21b1b\n" + //
        SOB1,//
        call("a\n" + //
            "\n" + //
            "Bug: 42\n" + //
            SOB1));

    assertEquals("a\n" + //
        "\n" + //
        "Issue: 42\n" + //
        "Change-Id: Ie66e07d89ae5b114c0975b49cf326e90331dd822\n" + //
        SOB1,//
        call("a\n" + //
            "\n" + //
            "Issue: 42\n" + //
            SOB1));
  public void testCommitDashV() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "Change-Id: I7fc3876fee63c766a2063df97fbe04a2dddd8d7c\n" + //
        SOB1 + //
        SOB2, //
        call("a\n" + //
            "\n" + //
            SOB1 + //
            SOB2 + //
            "\n" + //
            "# on branch master\n" + //
            "diff --git a/src b/src\n" + //
            "new file mode 100644\n" + //
            "index 0000000..c78b7f0\n"));
  public void testWithEndingURL() throws Exception {
    assertEquals("a\n" + //
        "\n" + //
        "http://example.com/ fixes this\n" + //
        "\n" + //
        "Change-Id: I3b7e4e16b503ce00f07ba6ad01d97a356dad7701\n", //
        call("a\n" + //
            "\n" + //
            "http://example.com/ fixes this\n"));
    assertEquals("a\n" + //
        "\n" + //
        "https://example.com/ fixes this\n" + //
        "\n" + //
        "Change-Id: I62b9039e2fc0dce274af55e8f99312a8a80a805d\n", //
        call("a\n" + //
            "\n" + //
            "https://example.com/ fixes this\n"));
    assertEquals("a\n" + //
        "\n" + //
        "ftp://example.com/ fixes this\n" + //
        "\n" + //
        "Change-Id: I71b05dc1f6b9a5540a53a693e64d58b65a8910e8\n", //
        call("a\n" + //
            "\n" + //
            "ftp://example.com/ fixes this\n"));
    assertEquals("a\n" + //
        "\n" + //
        "git://example.com/ fixes this\n" + //
        "\n" + //
        "Change-Id: Id34e942baa68d790633737d815ddf11bac9183e5\n", //
        call("a\n" + //
            "\n" + //
            "git://example.com/ fixes this\n"));
  public void testWithFalseTags() throws Exception {
    assertEquals("foo\n" + //
	"\n" + //
	"FakeLine:\n" + //
	"  foo\n" + //
	"  bar\n" + //
	"\n" + //
	"Change-Id: I67632a37fd2e08a35f766f52fc9a47f4ea868c55\n" + //
	"RealTag: abc\n", //
	call("foo\n" + //
	    "\n" + //
	    "FakeLine:\n" + //
	    "  foo\n" + //
	    "  bar\n" + //
	    "\n" + //
	    "RealTag: abc\n"));
  private void hookDoesNotModify(final String in) throws Exception {
  private String call(final String body) throws Exception {
  private DirCacheEntry file(final String name) throws IOException {
        .withFailureMessage(Constants.HEAD + " did not change: " + ref.getResult())
        .that(result)
        .isAnyOf(Result.FAST_FORWARD, Result.FORCED, Result.NEW, Result.NO_CHANGE);