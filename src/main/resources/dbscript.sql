USE [DB_StorylineTool]
GO
/****** Object:  Table [dbo].[tbl_User]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_User]
(
    [fld_UserID]   [int] IDENTITY (1,1) NOT NULL,
    [fld_Username] [nvarchar](30)       NULL,
    [tbl_Password] [nvarchar](30)       NULL,
    CONSTRAINT [PK__tbl_User__C851D2E6FFFB1532] PRIMARY KEY CLUSTERED
        (
         [fld_UserID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_UserEventCard]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_UserEventCard]
(
    [fld_UserEventCardID] [uniqueidentifier] NOT NULL,
    [fld_UserID]          [int]              NOT NULL,
    [fld_ColorString]     [nvarchar](30)     NOT NULL,
    [fld_EventContent]    [nvarchar](500)    NOT NULL,
    [fld_Title]           [nvarchar](20)     NOT NULL,
    CONSTRAINT [PK__tbl_User__2A3D29FDA7D346D4] PRIMARY KEY CLUSTERED
        (
         [fld_UserEventCardID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[viewUserEventCards]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[viewUserEventCards]
AS
SELECT dbo.tbl_UserEventCard.fld_UserEventCardID,
       dbo.tbl_UserEventCard.fld_ColorString,
       dbo.tbl_UserEventCard.fld_EventContent,
       dbo.tbl_UserEventCard.fld_Title,
       dbo.tbl_UserEventCard.fld_UserID
FROM dbo.tbl_User
         INNER JOIN
     dbo.tbl_UserEventCard ON dbo.tbl_User.fld_UserID = dbo.tbl_UserEventCard.fld_UserID
GO
/****** Object:  Table [dbo].[tbl_Timeline]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Timeline]
(
    [fld_TimelineID]   [uniqueidentifier] NOT NULL,
    [fld_TimelineName] [nvarchar](40)     NOT NULL,
    CONSTRAINT [PK__tbl_Time__4D68A8E989CB21BF] PRIMARY KEY CLUSTERED
        (
         [fld_TimelineID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_TimelineEventCard]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_TimelineEventCard]
(
    [fld_TimelineEventCardID] [uniqueidentifier] NOT NULL,
    [fld_TimelineID]          [uniqueidentifier] NOT NULL,
    [fld_Title]               [nvarchar](20)     NOT NULL,
    [fld_EventContent]        [nvarchar](500)    NOT NULL,
    [fld_ColorString]         [nvarchar](30)     NOT NULL,
    [fld_X]                   [int]              NOT NULL,
    [fld_Y]                   [int]              NOT NULL,
    CONSTRAINT [PK__tbl_Time__F52310F9A8707C5E] PRIMARY KEY CLUSTERED
        (
         [fld_TimelineEventCardID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[viewTimelineEventCards]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[viewTimelineEventCards]
AS
SELECT dbo.tbl_TimelineEventCard.*
FROM dbo.tbl_TimelineEventCard
         INNER JOIN
     dbo.tbl_Timeline ON dbo.tbl_TimelineEventCard.fld_TimelineID = dbo.tbl_Timeline.fld_TimelineID
GO
/****** Object:  Table [dbo].[tbl_Entity]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_Entity]
(
    [fld_EntityID]          [uniqueidentifier] NOT NULL,
    [fld_UserID]            [int]              NOT NULL,
    [fld_Initials]          [nvarchar](10)     NOT NULL,
    [fld_EntityName]        [nvarchar](50)     NOT NULL,
    [fld_EntityDescription] [nvarchar](200)    NOT NULL,
    CONSTRAINT [PK__tbl_Enti__664C679CC1E24A46] PRIMARY KEY CLUSTERED
        (
         [fld_EntityID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_TimelineEventCard_Entity]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_TimelineEventCard_Entity]
(
    [fld_TimelineEventCardID] [uniqueidentifier] NOT NULL,
    [fld_EntityID]            [uniqueidentifier] NOT NULL,
    CONSTRAINT [PK_tbl_TimelineEventCard_Entity] PRIMARY KEY CLUSTERED
        (
         [fld_TimelineEventCardID] ASC,
         [fld_EntityID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  View [dbo].[viewTimelineEventCardEntities]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE VIEW [dbo].[viewTimelineEventCardEntities]
AS
SELECT dbo.tbl_Entity.fld_UserID,
       dbo.tbl_Entity.fld_EntityID,
       dbo.tbl_Entity.fld_Initials,
       dbo.tbl_Entity.fld_EntityName,
       dbo.tbl_Entity.fld_EntityDescription,
       dbo.tbl_TimelineEventCard.fld_TimelineEventCardID
FROM dbo.tbl_Entity
         INNER JOIN
     dbo.tbl_TimelineEventCard_Entity ON dbo.tbl_Entity.fld_EntityID = dbo.tbl_TimelineEventCard_Entity.fld_EntityID
         INNER JOIN
     dbo.tbl_TimelineEventCard
     ON dbo.tbl_TimelineEventCard_Entity.fld_TimelineEventCardID = dbo.tbl_TimelineEventCard.fld_TimelineEventCardID
GO
/****** Object:  Table [dbo].[tbl_User_Timeline]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_User_Timeline]
(
    [fld_UserID]     [int]              NOT NULL,
    [fld_TimelineID] [uniqueidentifier] NOT NULL,
    CONSTRAINT [PK_tbl_User_Timeline] PRIMARY KEY CLUSTERED
        (
         [fld_UserID] ASC,
         [fld_TimelineID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[tbl_UserEventCard_Entity]    Script Date: 08-06-2021 22:14:01 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[tbl_UserEventCard_Entity]
(
    [fld_EntityID]        [uniqueidentifier] NOT NULL,
    [fld_UserEventCardID] [uniqueidentifier] NOT NULL,
    CONSTRAINT [PK_tbl_UserEventCard_Entity] PRIMARY KEY CLUSTERED
        (
         [fld_EntityID] ASC,
         [fld_UserEventCardID] ASC
            ) WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[tbl_Entity]
    WITH CHECK ADD CONSTRAINT [FK_tbl_Entity_tbl_User] FOREIGN KEY ([fld_UserID])
        REFERENCES [dbo].[tbl_User] ([fld_UserID])
GO
ALTER TABLE [dbo].[tbl_Entity]
    CHECK CONSTRAINT [FK_tbl_Entity_tbl_User]
GO
ALTER TABLE [dbo].[tbl_TimelineEventCard]
    WITH CHECK ADD CONSTRAINT [FK_tbl_TimelineEventCard_tbl_Timeline] FOREIGN KEY ([fld_TimelineID])
        REFERENCES [dbo].[tbl_Timeline] ([fld_TimelineID])
GO
ALTER TABLE [dbo].[tbl_TimelineEventCard]
    CHECK CONSTRAINT [FK_tbl_TimelineEventCard_tbl_Timeline]
GO
ALTER TABLE [dbo].[tbl_TimelineEventCard_Entity]
    WITH CHECK ADD CONSTRAINT [FK_tbl_TimelineEventCard_Entity_tbl_Entity] FOREIGN KEY ([fld_EntityID])
        REFERENCES [dbo].[tbl_Entity] ([fld_EntityID])
GO
ALTER TABLE [dbo].[tbl_TimelineEventCard_Entity]
    CHECK CONSTRAINT [FK_tbl_TimelineEventCard_Entity_tbl_Entity]
GO
ALTER TABLE [dbo].[tbl_TimelineEventCard_Entity]
    WITH CHECK ADD CONSTRAINT [FK_tbl_TimelineEventCard_Entity_tbl_TimelineEventCard] FOREIGN KEY ([fld_TimelineEventCardID])
        REFERENCES [dbo].[tbl_TimelineEventCard] ([fld_TimelineEventCardID])
GO
ALTER TABLE [dbo].[tbl_TimelineEventCard_Entity]
    CHECK CONSTRAINT [FK_tbl_TimelineEventCard_Entity_tbl_TimelineEventCard]
GO
ALTER TABLE [dbo].[tbl_User_Timeline]
    WITH CHECK ADD CONSTRAINT [FK_tbl_User_Timeline_tbl_Timeline] FOREIGN KEY ([fld_TimelineID])
        REFERENCES [dbo].[tbl_Timeline] ([fld_TimelineID])
GO
ALTER TABLE [dbo].[tbl_User_Timeline]
    CHECK CONSTRAINT [FK_tbl_User_Timeline_tbl_Timeline]
GO
ALTER TABLE [dbo].[tbl_User_Timeline]
    WITH CHECK ADD CONSTRAINT [FK_tbl_User_Timeline_tbl_User] FOREIGN KEY ([fld_UserID])
        REFERENCES [dbo].[tbl_User] ([fld_UserID])
GO
ALTER TABLE [dbo].[tbl_User_Timeline]
    CHECK CONSTRAINT [FK_tbl_User_Timeline_tbl_User]
GO
ALTER TABLE [dbo].[tbl_UserEventCard_Entity]
    WITH CHECK ADD CONSTRAINT [FK_tbl_UserEventCard_Entity_tbl_Entity] FOREIGN KEY ([fld_EntityID])
        REFERENCES [dbo].[tbl_Entity] ([fld_EntityID])
GO
ALTER TABLE [dbo].[tbl_UserEventCard_Entity]
    CHECK CONSTRAINT [FK_tbl_UserEventCard_Entity_tbl_Entity]
GO
ALTER TABLE [dbo].[tbl_UserEventCard_Entity]
    WITH CHECK ADD CONSTRAINT [FK_tbl_UserEventCard_Entity_tbl_UserEventCard] FOREIGN KEY ([fld_UserEventCardID])
        REFERENCES [dbo].[tbl_UserEventCard] ([fld_UserEventCardID])
GO
ALTER TABLE [dbo].[tbl_UserEventCard_Entity]
    CHECK CONSTRAINT [FK_tbl_UserEventCard_Entity_tbl_UserEventCard]
GO
EXEC sys.sp_addextendedproperty @name=N''MS_DiagramPane1'', @value=N''[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin
    DesignProperties =
                Begin PaneConfigurations =
            Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
End
Begin
    PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
End
Begin
    PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
End
Begin
    PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
End
Begin
    PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
End
Begin
    PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
End
Begin
    PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
End
Begin
    PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
End
Begin
    PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
End
Begin
    PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
End
Begin
    PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
End
Begin
    PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
End
Begin
    PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
End
Begin
    PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
End
Begin
    PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
End ActivePaneConfig = 0
End
Begin
    DiagramPane =
                Begin Origin =
            Top = 0
         Left = 0
End
Begin
    Tables =
        Begin Table = "tbl_Entity"
    Begin
        Extent =
            Top = 7
               Left = 48
               Bottom = 170
               Right = 280
    End DisplayFlags = 280
            TopColumn = 1
End
Begin
    Table = "tbl_TimelineEventCard"
    Begin
        Extent =
            Top = 7
               Left = 328
               Bottom = 170
               Right = 586
    End DisplayFlags = 280
            TopColumn = 1
End
Begin
    Table = "tbl_TimelineEventCard_Entity"
    Begin
        Extent =
            Top = 7
               Left = 634
               Bottom = 126
               Right = 892
    End DisplayFlags = 280
            TopColumn = 0
End
End
End
Begin
    SQLPane =
        End
    Begin
        DataPane =
            Begin ParameterDefaults = ""
    End
    Begin
        ColumnWidths = 9
         Width = 284
         Width = 1200
         Width = 1200
         Width = 1200
         Width = 1200
         Width = 1200
         Width = 1200
         Width = 1200
         Width = 1200
    End
End
Begin
    CriteriaPane =
        Begin ColumnWidths = 11 Column = 1440
         Alias = 900
         Table = 1170
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1350
         SortOrder = 1410
         GroupBy = 1350
         Filter = 1350
         Or = 1350
         Or = 1350
         Or = 1350
End
End
End
'' , @level0type=N''SCHEMA'',@level0name=N''dbo'', @level1type=N''VIEW'',@level1name=N''viewTimelineEventCardEntities''
GO
EXEC sys.sp_addextendedproperty @name=N''MS_DiagramPaneCount'', @value=1 , @level0type=N''SCHEMA'',@level0name=N''dbo'', @level1type=N''VIEW'',@level1name=N''viewTimelineEventCardEntities''
GO
EXEC sys.sp_addextendedproperty @name=N''MS_DiagramPane1'', @value=N''[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin
    DesignProperties =
                Begin PaneConfigurations =
            Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[40] 4[20] 2[20] 3) )"
End
Begin
    PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
End
Begin
    PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
End
Begin
    PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
End
Begin
    PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
End
Begin
    PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
End
Begin
    PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
End
Begin
    PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
End
Begin
    PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
End
Begin
    PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
End
Begin
    PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
End
Begin
    PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
End
Begin
    PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
End
Begin
    PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
End
Begin
    PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
End ActivePaneConfig = 0
End
Begin
    DiagramPane =
                Begin Origin =
            Top = 0
         Left = 0
End
Begin
    Tables =
        Begin Table = "tbl_TimelineEventCard"
    Begin
        Extent =
            Top = 7
               Left = 48
               Bottom = 170
               Right = 306
    End DisplayFlags = 280
            TopColumn = 0
End
Begin
    Table = "tbl_Timeline"
    Begin
        Extent =
            Top = 7
               Left = 354
               Bottom = 126
               Right = 570
    End DisplayFlags = 280
            TopColumn = 0
End
End
End
Begin
    SQLPane =
        End
    Begin
        DataPane =
            Begin ParameterDefaults = ""
    End
    Begin
        ColumnWidths = 12
         Width = 284
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1200
         Width = 1200
         Width = 1200
    End
End
Begin
    CriteriaPane =
        Begin ColumnWidths = 11 Column = 1440
         Alias = 900
         Table = 1176
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1356
         SortOrder = 1416
         GroupBy = 1350
         Filter = 1356
         Or = 1350
         Or = 1350
         Or = 1350
End
End
End
'' , @level0type=N''SCHEMA'',@level0name=N''dbo'', @level1type=N''VIEW'',@level1name=N''viewTimelineEventCards''
GO
EXEC sys.sp_addextendedproperty @name=N''MS_DiagramPaneCount'', @value=1 , @level0type=N''SCHEMA'',@level0name=N''dbo'', @level1type=N''VIEW'',@level1name=N''viewTimelineEventCards''
GO
EXEC sys.sp_addextendedproperty @name=N''MS_DiagramPane1'', @value=N''[0E232FF0-B466-11cf-A24F-00AA00A3EFFF, 1.00]
Begin
    DesignProperties =
                Begin PaneConfigurations =
            Begin PaneConfiguration = 0
         NumPanes = 4
         Configuration = "(H (1[43] 4[19] 2[16] 3) )"
End
Begin
    PaneConfiguration = 1
         NumPanes = 3
         Configuration = "(H (1 [50] 4 [25] 3))"
End
Begin
    PaneConfiguration = 2
         NumPanes = 3
         Configuration = "(H (1 [50] 2 [25] 3))"
End
Begin
    PaneConfiguration = 3
         NumPanes = 3
         Configuration = "(H (4 [30] 2 [40] 3))"
End
Begin
    PaneConfiguration = 4
         NumPanes = 2
         Configuration = "(H (1 [56] 3))"
End
Begin
    PaneConfiguration = 5
         NumPanes = 2
         Configuration = "(H (2 [66] 3))"
End
Begin
    PaneConfiguration = 6
         NumPanes = 2
         Configuration = "(H (4 [50] 3))"
End
Begin
    PaneConfiguration = 7
         NumPanes = 1
         Configuration = "(V (3))"
End
Begin
    PaneConfiguration = 8
         NumPanes = 3
         Configuration = "(H (1[56] 4[18] 2) )"
End
Begin
    PaneConfiguration = 9
         NumPanes = 2
         Configuration = "(H (1 [75] 4))"
End
Begin
    PaneConfiguration = 10
         NumPanes = 2
         Configuration = "(H (1[66] 2) )"
End
Begin
    PaneConfiguration = 11
         NumPanes = 2
         Configuration = "(H (4 [60] 2))"
End
Begin
    PaneConfiguration = 12
         NumPanes = 1
         Configuration = "(H (1) )"
End
Begin
    PaneConfiguration = 13
         NumPanes = 1
         Configuration = "(V (4))"
End
Begin
    PaneConfiguration = 14
         NumPanes = 1
         Configuration = "(V (2))"
End ActivePaneConfig = 0
End
Begin
    DiagramPane =
                Begin Origin =
            Top = 0
         Left = 0
End
Begin
    Tables =
        Begin Table = "tbl_User"
    Begin
        Extent =
            Top = 6
               Left = 274
               Bottom = 119
               Right = 444
    End DisplayFlags = 280
            TopColumn = 0
End
Begin
    Table = "tbl_UserEventCard"
    Begin
        Extent =
            Top = 6
               Left = 482
               Bottom = 136
               Right = 678
    End DisplayFlags = 280
            TopColumn = 0
End
End
End
Begin
    SQLPane =
        End
    Begin
        DataPane =
            Begin ParameterDefaults = ""
    End
    Begin
        ColumnWidths = 9
         Width = 284
         Width = 2340
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
         Width = 1500
    End
End
Begin
    CriteriaPane =
        Begin ColumnWidths = 11 Column = 2316
         Alias = 900
         Table = 1176
         Output = 720
         Append = 1400
         NewValue = 1170
         SortType = 1356
         SortOrder = 1416
         GroupBy = 1350
         Filter = 1356
         Or = 1350
         Or = 1350
         Or = 1350
End
End
End
'' , @level0type=N''SCHEMA'',@level0name=N''dbo'', @level1type=N''VIEW'',@level1name=N''viewUserEventCards''
GO
EXEC sys.sp_addextendedproperty @name=N''MS_DiagramPaneCount'', @value=1 , @level0type=N''SCHEMA'',@level0name=N''dbo'', @level1type=N''VIEW'',@level1name=N''viewUserEventCards''
GO
