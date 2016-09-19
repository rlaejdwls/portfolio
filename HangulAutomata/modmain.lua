_G=GLOBAL

if not _G.rawget(_G,"mods") then _G.rawset(_G,"mods",{}) end
if not _G.mods.KoreanChat then
	_G.mods.KoreanChat = {}

	local kor_toggle = GetModConfigData("default_language")

	local function ConvertNick(msg)
		local result = msg
		-- for i = 1, string.len(msg) do
		-- 	result = result..string.byte(msg, i)
		-- end
		return result
	end

	local InputType = {
		INITIAL_CHAR 		= 0,
		MIDDLE_CHAR_FIRST 	= 1,
		MIDDLE_CHAR_NEXT 	= 2,
		FINAL_CHAR_FIRST 	= 3,
		FINAL_CHAR_NEXT 	= 4
	}

	local KorType = {
		INITIAL = 0,
		MIDDLE 	= 1,
		FINAL 	= 2,
		OTHER 	= 3
	}

	local Position = {
	    LEFT 				= 0,
	    TOP 				= 1,
	    LEFT_AND_TOP		= 2,
	    LEFT_AND_TOP_SMALL 	= 3,
	    RIGHT 				= 4,
	    RIGHT_AND_TOP 		= 5,
	    BOTTOM 				= 6,
	    MIDDLE 				= 7,
	    BOTTOM_ON_RIGHT 	= 8,
	    MIDDLE_ON_RIGHT 	= 9,
	    FINAL 				= 10
	}

	local init_table = { 
		{
			index = 0, en = { 114 }, kr = 'ㄱ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\1", position = Position.LEFT },
				{ code = "\2", position = Position.TOP },
				{ code = "\3", position = Position.LEFT_AND_TOP },
				{ code = "\4", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 1, en = { 82 }, kr = 'ㄲ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\167", position = Position.LEFT },
				{ code = "\168", position = Position.TOP },
				{ code = "\169", position = Position.LEFT_AND_TOP },
				{ code = "\170", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 2, en = { 115 }, kr = 'ㄴ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\5", position = Position.LEFT },
				{ code = "\6", position = Position.TOP },
				{ code = "\7", position = Position.LEFT_AND_TOP },
				{ code = "\11", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 3, en = { 101 }, kr = 'ㄷ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\12", position = Position.LEFT },
				{ code = "\14", position = Position.TOP },
				{ code = "\15", position = Position.LEFT_AND_TOP },
				{ code = "\16", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 4, en = { 69 }, kr = 'ㄸ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\171", position = Position.LEFT },
				{ code = "\172", position = Position.TOP },
				{ code = "\173", position = Position.LEFT_AND_TOP },
				{ code = "\174", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 5, en = { 102 }, kr = 'ㄹ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\17", position = Position.LEFT },
				{ code = "\18", position = Position.TOP },
				{ code = "\19", position = Position.LEFT_AND_TOP },
				{ code = "\20", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 6, en = { 97 }, kr = 'ㅁ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\21", position = Position.LEFT },
				{ code = "\22", position = Position.TOP },
				{ code = "\23", position = Position.LEFT_AND_TOP },
				{ code = "\24", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 7, en = { 113 }, kr = 'ㅂ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\25", position = Position.LEFT },
				{ code = "\26", position = Position.TOP },
				{ code = "\28", position = Position.LEFT_AND_TOP },
				{ code = "\29", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 8, en = { 81 }, kr = 'ㅃ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\175", position = Position.LEFT },
				{ code = "\176", position = Position.TOP },
				{ code = "\177", position = Position.LEFT_AND_TOP },
				{ code = "\178", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 9, en = { 116 }, kr = 'ㅅ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\30", position = Position.LEFT },
				{ code = "\31", position = Position.TOP },
				{ code = "\123", position = Position.LEFT_AND_TOP },
				{ code = "\124", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 10, en = { 84 }, kr = 'ㅆ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\179", position = Position.LEFT },
				{ code = "\180", position = Position.TOP },
				{ code = "\181", position = Position.LEFT_AND_TOP },
				{ code = "\182", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 11, en = { 100 }, kr = 'ㅇ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\125", position = Position.LEFT },
				{ code = "\126", position = Position.TOP },
				{ code = "\134", position = Position.LEFT_AND_TOP },
				{ code = "\137", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 12, en = { 119 }, kr = 'ㅈ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\143", position = Position.LEFT },
				{ code = "\144", position = Position.TOP },
				{ code = "\145", position = Position.LEFT_AND_TOP },
				{ code = "\146", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 13, en = { 87 }, kr = 'ㅉ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\183", position = Position.LEFT },
				{ code = "\184", position = Position.TOP },
				{ code = "\185", position = Position.LEFT_AND_TOP },
				{ code = "\186", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 14, en = { 99 }, kr = 'ㅊ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\147", position = Position.LEFT },
				{ code = "\148", position = Position.TOP },
				{ code = "\149", position = Position.LEFT_AND_TOP },
				{ code = "\150", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 15, en = { 122 }, kr = 'ㅋ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\151", position = Position.LEFT },
				{ code = "\152", position = Position.TOP },
				{ code = "\153", position = Position.LEFT_AND_TOP },
				{ code = "\154", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 16, en = { 120 }, kr = 'ㅌ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\155", position = Position.LEFT },
				{ code = "\156", position = Position.TOP },
				{ code = "\157", position = Position.LEFT_AND_TOP },
				{ code = "\158", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 17, en = { 118 }, kr = 'ㅍ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\159", position = Position.LEFT },
				{ code = "\160", position = Position.TOP },
				{ code = "\161", position = Position.LEFT_AND_TOP },
				{ code = "\162", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 18, en = { 103 }, kr = 'ㅎ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\163", position = Position.LEFT },
				{ code = "\164", position = Position.TOP },
				{ code = "\165", position = Position.LEFT_AND_TOP },
				{ code = "\166", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 2, en = { 83 }, kr = 'ㄴ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\5", position = Position.LEFT },
				{ code = "\6", position = Position.TOP },
				{ code = "\7", position = Position.LEFT_AND_TOP },
				{ code = "\11", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 5, en = { 70 }, kr = 'ㄹ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\17", position = Position.LEFT },
				{ code = "\18", position = Position.TOP },
				{ code = "\19", position = Position.LEFT_AND_TOP },
				{ code = "\20", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 6, en = { 65 }, kr = 'ㅁ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\21", position = Position.LEFT },
				{ code = "\22", position = Position.TOP },
				{ code = "\23", position = Position.LEFT_AND_TOP },
				{ code = "\24", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 11, en = { 68 }, kr = 'ㅇ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\125", position = Position.LEFT },
				{ code = "\126", position = Position.TOP },
				{ code = "\134", position = Position.LEFT_AND_TOP },
				{ code = "\137", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 14, en = { 67 }, kr = 'ㅊ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\147", position = Position.LEFT },
				{ code = "\148", position = Position.TOP },
				{ code = "\149", position = Position.LEFT_AND_TOP },
				{ code = "\150", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 15, en = { 90 }, kr = 'ㅋ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\151", position = Position.LEFT },
				{ code = "\152", position = Position.TOP },
				{ code = "\153", position = Position.LEFT_AND_TOP },
				{ code = "\154", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 16, en = { 88 }, kr = 'ㅌ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\155", position = Position.LEFT },
				{ code = "\156", position = Position.TOP },
				{ code = "\157", position = Position.LEFT_AND_TOP },
				{ code = "\158", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 17, en = { 86 }, kr = 'ㅍ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\159", position = Position.LEFT },
				{ code = "\160", position = Position.TOP },
				{ code = "\161", position = Position.LEFT_AND_TOP },
				{ code = "\162", position = Position.LEFT_AND_TOP_SMALL }
			}
		}, 
		{
			index = 18, en = { 71 }, kr = 'ㅎ', kor_type = KorType.INITIAL, key_code = {
				{ code = "\163", position = Position.LEFT },
				{ code = "\164", position = Position.TOP },
				{ code = "\165", position = Position.LEFT_AND_TOP },
				{ code = "\166", position = Position.LEFT_AND_TOP_SMALL }
			}
		}
	}

	local mid_table = { 
		{
			index = 0, en = { 107 }, kr = 'ㅏ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\187", position = Position.RIGHT },
				{ code = "\188", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 1, en = { 111 }, kr = 'ㅐ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\209", position = Position.RIGHT },
				{ code = "\210", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 2, en = { 105 }, kr = 'ㅑ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\189", position = Position.RIGHT },
				{ code = "\190", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 3, en = { 79 }, kr = 'ㅒ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\211", position = Position.RIGHT },
				{ code = "\212", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 4, en = { 106 }, kr = 'ㅓ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\191", position = Position.RIGHT },
				{ code = "\192", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 5, en = { 112 }, kr = 'ㅔ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\213", position = Position.RIGHT },
				{ code = "\214", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 6, en = { 117 }, kr = 'ㅕ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\193", position = Position.RIGHT },
				{ code = "\194", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 7, en = { 80 }, kr = 'ㅖ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\215", position = Position.RIGHT },
				{ code = "\216", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 8, en = { 104 }, kr = 'ㅗ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\195", position = Position.BOTTOM },
				{ code = "\196", position = Position.MIDDLE }
			}
		},
		{
			index = 9, en = { 104, 107 }, kr = 'ㅘ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\217", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\218", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 10, en = { 104, 111 }, kr = 'ㅙ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\221", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\222", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 11, en = { 104, 108 }, kr = 'ㅚ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\219", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\220", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 12, en = { 121 }, kr = 'ㅛ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\197", position = Position.BOTTOM },
				{ code = "\198", position = Position.MIDDLE }
			}
		},
		{
			index = 13, en = { 110 }, kr = 'ㅜ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\199", position = Position.BOTTOM },
				{ code = "\200", position = Position.MIDDLE }
			}
		},
		{
			index = 14, en = { 110, 106 }, kr = 'ㅝ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\223", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\224", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 15, en = { 110, 112 }, kr = 'ㅞ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\227", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\228", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 16, en = { 110, 108 }, kr = 'ㅟ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\225", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\226", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 17, en = { 98 }, kr = 'ㅠ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\201", position = Position.BOTTOM },
				{ code = "\202", position = Position.MIDDLE }
			}
		},
		{
			index = 18, en = { 109 }, kr = 'ㅡ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\203", position = Position.BOTTOM },
				{ code = "\204", position = Position.MIDDLE }
			}
		},
		{
			index = 19, en = { 109, 108 }, kr = 'ㅢ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\207", position = Position.BOTTOM_ON_RIGHT },
				{ code = "\208", position = Position.MIDDLE_ON_RIGHT }
			}
		},
		{
			index = 20, en = { 108 }, kr = 'ㅣ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\205", position = Position.RIGHT },
				{ code = "\206", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 0, en = { 75 }, kr = 'ㅏ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\187", position = Position.RIGHT },
				{ code = "\188", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 2, en = { 73 }, kr = 'ㅑ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\189", position = Position.RIGHT },
				{ code = "\190", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 4, en = { 74 }, kr = 'ㅓ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\191", position = Position.RIGHT },
				{ code = "\192", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 6, en = { 85 }, kr = 'ㅕ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\193", position = Position.RIGHT },
				{ code = "\194", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 20, en = { 76 }, kr = 'ㅣ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\205", position = Position.RIGHT },
				{ code = "\206", position = Position.RIGHT_AND_TOP }
			}
		},
		{
			index = 8, en = { 72 }, kr = 'ㅗ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\195", position = Position.BOTTOM },
				{ code = "\196", position = Position.MIDDLE }
			}
		},
		{
			index = 12, en = { 89 }, kr = 'ㅛ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\197", position = Position.BOTTOM },
				{ code = "\198", position = Position.MIDDLE }
			}
		},
		{
			index = 13, en = { 78 }, kr = 'ㅜ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\199", position = Position.BOTTOM },
				{ code = "\200", position = Position.MIDDLE }
			}
		},
		{
			index = 17, en = { 66 }, kr = 'ㅠ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\201", position = Position.BOTTOM },
				{ code = "\202", position = Position.MIDDLE }
			}
		},
		{
			index = 18, en = { 77 }, kr = 'ㅡ', kor_type = KorType.MIDDLE, key_code = {
				{ code = "\203", position = Position.BOTTOM },
				{ code = "\204", position = Position.MIDDLE }
			}
		}
	}

--	{ 'r', 'R', 'rt', 's', 'sw', 'sg', 'e', 'f', 'fr', 'fa', 'fq', 'ft', 'fx', 'fv', 'fg', 'a', 'q', 'qt', 't', 'T', 'd', 'w', 'c', 'z', 'x', 'v', 'g' }
	local final_table = { 
		{
			index = 0, en = { 0 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\0", position = Position.FINAL }
			}
		},
		{
			index = 1, en = { 114 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\229", position = Position.FINAL }
			}
		},
		{
			index = 2, en = { 82 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\243", position = Position.FINAL }
			}
		},
		{
			index = 3, en = { 114, 116 }, kr = '', kor_type = KorType.FINAL, key_code = nil
		},
		{
			index = 4, en = { 115 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\230", position = Position.FINAL }
			}
		},
		{
			index = 5, en = { 115, 119 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\244", position = Position.FINAL }
			}
		},
		{
			index = 6, en = { 115, 103 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\245", position = Position.FINAL }
			}
		},
		{
			index = 7, en = { 101 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\231", position = Position.FINAL }
			}
		},
		{
			index = 8, en = { 102 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\232", position = Position.FINAL }
			}
		},
		{
			index = 9, en = { 102, 114 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\246", position = Position.FINAL }
			}
		},
		{
			index = 10, en = { 102, 97 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\247", position = Position.FINAL }
			}
		},
		{
			index = 11, en = { 102, 113 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\248", position = Position.FINAL }
			}
		},
		{
			index = 12, en = { 102, 116 }, kr = '', kor_type = KorType.FINAL, key_code = nil
		},
		{
			index = 13, en = { 102, 120 }, kr = '', kor_type = KorType.FINAL, key_code = nil
		},
		{
			index = 14, en = { 102, 118 }, kr = '', kor_type = KorType.FINAL, key_code = nil
		},
		{
			index = 15, en = { 102, 103 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\249", position = Position.FINAL }
			}
		},
		{
			index = 16, en = { 97 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\233", position = Position.FINAL }
			}
		},
		{
			index = 17, en = { 113 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\234", position = Position.FINAL }
			}
		},
		{
			index = 18, en = { 113, 116 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\250", position = Position.FINAL }
			}
		},
		{
			index = 19, en = { 116 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\235", position = Position.FINAL }
			}
		},
		{
			index = 20, en = { 84 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\251", position = Position.FINAL }
			}
		},
		{
			index = 21, en = { 100 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\236", position = Position.FINAL }
			}
		},
		{
			index = 22, en = { 119 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\237", position = Position.FINAL }
			}
		},
		{
			index = 23, en = { 99 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\238", position = Position.FINAL }
			}
		},
		{
			index = 24, en = { 122 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\239", position = Position.FINAL }
			}
		},
		{
			index = 25, en = { 120 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\240", position = Position.FINAL }
			}
		},
		{
			index = 26, en = { 118 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\241", position = Position.FINAL }
			}
		},
		{
			index = 27, en = { 103 }, kr = '', kor_type = KorType.FINAL, key_code = {
				{ code = "\242", position = Position.FINAL }
			}
		}
	}

	local code_list = {}
	local current_input_type = InputType.INITIAL_CHAR
	local current_index = -1

	local function Initial()
		code_list = {}
		current_input_type = InputType.INITIAL_CHAR
		current_index = -1
	end

	local function GetLength(obj_table)
		local count = 0

		if obj_table.length == nil then
			for i, v in pairs(obj_table) do	
				count = count + 1
			end
			obj_table.length = count
		end
		return obj_table.length
	end

	local function Copy(empty, source)
		empty.index = source.index
		empty.en = source.en
		empty.kr = source.kr
		empty.kor_type = source.kor_type
		empty.key_code = source.key_code
	end

	local function SetCodeAuto(code_model)
		if code_model.key_code == nil then return end

		if code_model.kor_type == KorType.INITIAL then
			code_model.code = GetKeyCode(code_model, Position.LEFT)
		elseif code_model.kor_type == KorType.MIDDLE then
			if string.find("1;2;3;4;5;6;7;8;21;", code_model.index..";") ~= nil then
				code_model.code = GetKeyCode(code_model, Position.RIGHT)
			elseif string.find("9;13;14;18;19;", code_model.index..";") ~= nil then
				code_model.code = GetKeyCode(code_model, Position.BOTTOM)
			elseif string.find("10;11;12;15;16;17;20;", code_model.index..";") ~= nil then
				code_model.code = GetKeyCode(code_model, Position.BOTTOM_ON_RIGHT)
			end
		elseif code_model.kor_type == KorType.FINAL then
			code_model.code = GetKeyCode(code_model, Position.FINAL)
		end
	end

	local function SetCode(change_code_model, current_code_model)
		if current_code_model.kor_type == KorType.MIDDLE then
			if current_code_model.code ~= nil then
				if current_code_model.code.position == Position.RIGHT then
					change_code_model.code = GetKeyCode(change_code_model, Position.LEFT)
				elseif (current_code_model.code.position == Position.BOTTOM) or
					(current_code_model.code.position == Position.MIDDLE) then
					change_code_model.code = GetKeyCode(change_code_model, Position.TOP)
				elseif (current_code_model.code.position == Position.BOTTOM_ON_RIGHT) or
					(current_code_model.code.position == Position.MIDDLE_ON_RIGHT) then
					change_code_model.code = GetKeyCode(change_code_model, Position.LEFT_AND_TOP)
				elseif current_code_model.code.position == Position.RIGHT_AND_TOP then
					change_code_model.code = GetKeyCode(change_code_model, Position.LEFT_AND_TOP_SMALL)
				end
			end
		elseif current_code_model.kor_type == KorType.FINAL then
			if change_code_model.code ~= nil then
				if change_code_model.code.position == Position.LEFT then
					change_code_model.code = GetKeyCode(change_code_model, Position.LEFT_AND_TOP_SMALL)
				elseif change_code_model.code.position == Position.RIGHT then
					change_code_model.code = GetKeyCode(change_code_model, Position.RIGHT_AND_TOP)
				elseif change_code_model.code.position == Position.BOTTOM then
					change_code_model.code = GetKeyCode(change_code_model, Position.MIDDLE)
				elseif change_code_model.code.position == Position.BOTTOM_ON_RIGHT then
					change_code_model.code = GetKeyCode(change_code_model, Position.MIDDLE_ON_RIGHT)
				end
			end
		end
	end

	-- local function GetKeyCode(code_model, position)
	function GetKeyCode(code_model, position)
		if code_model.key_code == nil then return nil end

		for i = 1, GetLength(code_model.key_code) do
			if code_model.key_code[i].position == position then return code_model.key_code[i] end
		end
		return nil
	end

	local function GetEncodeString()
		local result = ""
		-- local large_spec_space = "\253\253\253\253"
		-- local spec_space = "\253\253"
		-- local small_spec_space = "\253"
		-- local large_char_space = "\253\253\253\253\253\253\253\253\253\253"
		-- local char_space = "\253\253\253\253\253\253\253\253\253"
		-- local small_char_space = "\253\253\253\253\253\253\253\253"

		for i = 1, (GetLength(code_list)-1) do
			local models = code_list[i]

			if models[1] ~= nil then
				if models[1].index == -1 then
					result = result..string.char(models[1].en)
				else
					if models[2] == nil then
						result = result..models[1].kr
					else
						-- print(GetUnicode(models))
						result = result..conv2utf8({GetUnicode(models)})
					end
				end
			else 
				if models[2] ~= nil then
					result = result..models[2].kr
				end
			end

			-- if (models[1] ~= nil) and models[1].key_code ~= nil then 
			-- 	if string.find(";2;5;9;11;14;", ";"..models[1].index..";") ~= nil then
			-- 		result = result..large_char_space 
			-- 	else
			-- 		result = result..char_space
			-- 	end
			-- elseif (models[2] ~= nil) and models[2].key_code ~= nil then 
			-- 	result = result..small_char_space 
			-- end
			-- for i in pairs(models) do
			-- 	local model = models[i]
			-- 	if model ~= nil then
			-- 		if model.code ~= nil then
			-- 			result = result..model.code.code
			-- 		else
			-- 			if model.index == -1 then
			-- 				result = result..string.char(model.en)
			-- 			end
			-- 		end
			-- 	end
			-- end
			-- if (models[1] ~= nil) and (models[1].index == -1) then
			-- 	if (models[1].en == 32) or (models[1].en == 33) then
			-- 		result = result..small_spec_space
			-- 	else
			-- 		result = result..spec_space
			-- 	end
			-- end
		end
		return result
	end

	local function SearchByEn(start, kor_table, en, index)
		for i = start, GetLength(kor_table) do
			-- print("SearchByEn::"..GetLength(kor_table[i].en))
			if GetLength(kor_table[i].en) < index then return nil end
			if kor_table[i].en[index] == en then return kor_table[i] end
		end
		return nil
	end

	function GetUnicode(codeset)
		if codeset[3] ~= nil then
			return 44032 + (codeset[1].index * 21 * 28 + codeset[2].index * 28 + codeset[3].index)
		elseif codeset[2] ~= nil then
			return 44032 + (codeset[1].index * 21 * 28 + codeset[2].index * 28)
		elseif codeset[1] ~= nil then
			return 44032 + (codeset[1].index * 21 * 28)
		end
	end

	local function Make(data)
		-- print(current_input_type)
		-- print(data)
		local exit = false

		local search = nil
		local code_model = {}
		local codeset = {}

		while exit == false do
			if current_input_type == InputType.INITIAL_CHAR then
				search = SearchByEn(1, init_table, data, 1)
				if search ~= nil then
					Copy(code_model, search)
					SetCodeAuto(code_model)

					codeset[1] = code_model
					codeset[2] = nil
					codeset[3] = nil
					table.insert(code_list, codeset)
					code_list.length = GetLength(code_list) + 1
					current_index = current_index + 1
					exit = true
				end
				current_input_type = InputType.MIDDLE_CHAR_FIRST
			elseif current_input_type == InputType.MIDDLE_CHAR_FIRST then
				search = SearchByEn(1, mid_table, data, 1)
				if search ~= nil then
					if current_index == -1 then 
						Copy(code_model, search)
						SetCodeAuto(code_model)

						codeset[1] = nil
						codeset[2] = code_model
						codeset[3] = nil
						table.insert(code_list, codeset)
						code_list.length = GetLength(code_list) + 1
						current_index = current_index + 1
						current_input_type = InputType.INITIAL_CHAR
						return
					end

					codeset = code_list[GetLength(code_list)-1]

					if codeset[3] ~= nil then
						current_input_type = InputType.INITIAL_CHAR
						if GetLength(codeset[3].en) == 1 then
							Make(codeset[3].en[1])
							Make(data)
							codeset[3] = nil
							SetCodeAuto(codeset[2])
							SetCode(codeset[1], codeset[2])
						else
							ch = codeset[3].en[2]
							current_input_type = InputType.FINAL_CHAR_FIRST
							Make(codeset[3].en[1])
							current_input_type = InputType.INITIAL_CHAR
							Make(ch)
							Make(data)
						end
						exit = true
					else
						if codeset[2] ~= nil then
							Copy(code_model, search)
							SetCodeAuto(code_model)

							codeset = {}
							codeset[2] = code_model
							table.insert(code_list, codeset)
							code_list.length = GetLength(code_list) + 1
							current_index = current_index + 1
							current_input_type = InputType.INITIAL_CHAR
						else
							Copy(code_model, search)
							SetCodeAuto(code_model)
							codeset[2] = code_model
							SetCode(codeset[1], code_model)

							current_input_type = InputType.MIDDLE_CHAR_NEXT
						end
						exit = true
					end
				else
					search = SearchByEn(1, init_table, data, 1)
					if search ~= nil then
						current_input_type = InputType.INITIAL_CHAR
						Make(data)
						exit = true
					else
						code_model.index = -1
						code_model.en = data
						code_model.kor_type = KorType.OTHER

						codeset[1] = code_model
						table.insert(code_list, codeset)
						code_list.length = GetLength(code_list) + 1
						current_input_type = InputType.INITIAL_CHAR
						exit = true
					end
				end
			elseif current_input_type == InputType.MIDDLE_CHAR_NEXT then
				-- print("middle_char_next")
				-- print(code_list[GetLength(code_list)-1][2].index+1)
				-- print(data)
				search = SearchByEn(code_list[GetLength(code_list)-1][2].index+2, mid_table, data, 2)
				if search ~= nil then
					-- print(search.index)
					codeset = code_list[GetLength(code_list)-1]
					Copy(code_model, search)
					SetCodeAuto(code_model)
					codeset[2] = code_model
					SetCode(codeset[1], code_model)
					exit = true
				end
				current_input_type = InputType.FINAL_CHAR_FIRST
			elseif current_input_type == InputType.FINAL_CHAR_FIRST then
				search = SearchByEn(1, final_table, data, 1)
				if search ~= nil then
					codeset = code_list[GetLength(code_list)-1]
					Copy(code_model, search)
					SetCodeAuto(code_model)
					codeset[3] = code_model
					SetCode(codeset[1], code_model)
					SetCode(codeset[2], code_model)
					current_input_type = InputType.FINAL_CHAR_NEXT
					exit = true
				else
					current_input_type = InputType.INITIAL_CHAR
				end
			elseif current_input_type == InputType.FINAL_CHAR_NEXT then
				local plus = 1
				if code_list[GetLength(code_list)-1][3].en[1] == 114 then 
					plus = 3 
				else 
					plus = 2
				end

				search = SearchByEn(code_list[GetLength(code_list)-1][3].index+plus, final_table, data, 2)
				if search ~= nil then
					codeset = code_list[GetLength(code_list)-1]
					Copy(code_model, search)
					SetCodeAuto(code_model)
					codeset[3] = code_model
					exit = true
				end
				current_input_type = InputType.INITIAL_CHAR
			end
		end
	end

	function conv2utf8(unicode_list)
		local result = ''
		local w,x,y,z = 0,0,0,0
		local function modulo(a, b)
			return a - math.floor(a/b) * b
		end

		for i,v in ipairs(unicode_list) do
			if v ~= 0 and v ~= nil then
				if v <= 0x7F then -- same as ASCII
					result = result .. string.char(v)
				elseif v >= 0x80 and v <= 0x7FF then -- 2 bytes
					y = math.floor(modulo(v, 0x000800) / 64)
					z = modulo(v, 0x000040)
					result = result .. string.char(0xC0 + y, 0x80 + z)
				elseif (v >= 0x800 and v <= 0xD7FF) or (v >= 0xE000 and v <= 0xFFFF) then -- 3 bytes
					x = math.floor(modulo(v, 0x010000) / 4096)
					y = math.floor(modulo(v, 0x001000) / 64)
					z = modulo(v, 0x000040)
					result = result .. string.char(0xE0 + x, 0x80 + y, 0x80 + z)
				elseif (v >= 0x10000 and v <= 0x10FFFF) then -- 4 bytes
					w = math.floor(modulo(v, 0x200000) / 262144)
					x = math.floor(modulo(v, 0x040000) / 4096)
					y = math.floor(modulo(v, 0x001000) / 64)
					z = modulo(v, 0x000040)
					result = result .. string.char(0xF0 + w, 0x80 + x, 0x80 + y, 0x80 + z)
				end
			end
		end
		return result
	end

	local function MakeUp(data)
		for i = 1, string.len(data) do
			Make(string.byte(data, i))
		end
	end

	local function ConvertChat(guid, userid, name, prefab, msg)
		if msg == "&kr" then
			kor_toggle = "&kr"
		elseif msg == "&en" then
			kor_toggle = "&en"
		end

		if (msg ~= nil) and (kor_toggle == "&kr") and (msg ~= "&kr") then
			print("msg::"..msg)

			Initial()
			MakeUp(msg)
			if msg == "&test" then return conv2utf8({44033}) end
			if msg == "&love" then return "사랑해요~ 여러분" end
			return GetEncodeString()
		else
			return msg
		end
	end

	if _G.TheNet.Announce then
		_G.getmetatable(_G.TheNet).__index.Announce = (function()
			local oldAnnounce = _G.getmetatable(_G.TheNet).__index.Announce
			return function(self, msg, ... )
				if msg and type(msg)=="string" then
					msg=ConvertNick(msg)
				end
				oldAnnounce(self, msg, ...)
			end
		end)()
	end

	if _G.TheNet.GetClientTable then
		_G.getmetatable(_G.TheNet).__index.GetClientTable = (function()
			local oldGetClientTable = _G.getmetatable(_G.TheNet).__index.GetClientTable
			return function(self, ... )
				local res=oldGetClientTable(self, ...)
				if res and type(res)=="table" then for i,v in pairs(res) do
					if v.name and v.prefab then
						v.name=ConvertNick(v.name ) or v.name
						--if v.name=="[Host]" then v.name="[Хост]" end
						--AllPlayersList[v.name]=v.prefab or nil
					end
				end end
				return res
			end
		end)()
	end

	if _G.Network and _G.Network.GetClientName then
		local OldGetClientName=_G.Network.GetClientName
		function _G.Network:GetClientName(...)
			local res=OldGetClientName(self,...)
			res=ConvertNick(res)
			return(res)
		end
	end

	AddClassPostConstruct("widgets/eventannouncer", function(self)
		local oldGetNewDeathAnnouncementString=GLOBAL.GetNewDeathAnnouncementString
		function newGetNewDeathAnnouncementString(theDead, source, pkname)
			local str=oldGetNewDeathAnnouncementString(theDead, source, pkname)
			return ConvertNick(str)
		end
		GLOBAL.GetNewDeathAnnouncementString=newGetNewDeathAnnouncementString
		
		local oldGetNewRezAnnouncementString=GLOBAL.GetNewRezAnnouncementString
		function NewGetNewRezAnnouncementString(theRezzed, source, ...)
			return ConvertNick(oldGetNewRezAnnouncementString(theRezzed, source, ...))
		end
		GLOBAL.GetNewRezAnnouncementString=NewGetNewRezAnnouncementString

		self.oldShowNewAnnouncement=self.ShowNewAnnouncement
		local function newShowNewAnnouncement(self, announcement, ...)
			announcement=ConvertNick(announcement)
			return self:oldShowNewAnnouncement(announcement, ...)
		end
		self.ShowNewAnnouncement=newShowNewAnnouncement
	end)

	if GLOBAL.rawget(GLOBAL,"Networking_Talk") then
		local OldNetworking_Talk=GLOBAL.Networking_Talk
		
		function Networking_Talk(guid, message, ...)
			local entity = GLOBAL.Ents[guid]
			message=ConvertNick(message)
			if OldNetworking_Talk then OldNetworking_Talk(guid, message, ...) end
		end
		GLOBAL.Networking_Talk=Networking_Talk
	end

	if GLOBAL.rawget(GLOBAL,"Networking_Say") then
		local OldNetworking_Say=GLOBAL.Networking_Say
		
		function Networking_Say(guid, userid, name, prefab, message, ...)
			name=ConvertNick(name)
			message=ConvertChat(guid, userid, name, prefab, message)
			if OldNetworking_Say and message ~= "&en" and message ~= "&kr" then 
				OldNetworking_Say(guid, userid, name, prefab, message, ...) 
			end
		end
		GLOBAL.Networking_Say=Networking_Say
	end

	if GLOBAL.TheNet.Talker then
		GLOBAL.getmetatable(GLOBAL.TheNet).__index.Talker = (function()
			local oldTalker = GLOBAL.getmetatable(GLOBAL.TheNet).__index.Talker
			return function(self, message, entity, ... )
				oldTalker(self, message, entity, ...)

				local inst=entity and entity:GetGUID() or nil 
				inst=inst and GLOBAL.Ents[inst] or nil 
				if inst and inst.components.talker.widget then 
					message=ConvertChat(message)
					if message and type(message)=="string" then
						inst.components.talker.widget.text:SetString(message)
					end
				end
			end
		end)()
	end

	AddPrefabPostInit("skeleton_player", function(inst)
		function reassignfn(inst)
			inst.components.inspectable.Oldgetspecialdescription=inst.components.inspectable.getspecialdescription
			function Newgetspecialdescription(inst, viewer, ...)
				local message=inst.components.inspectable.Oldgetspecialdescription(inst, viewer, ...)
				return ConvertNick(message)
			end
			inst.components.inspectable.getspecialdescription=Newgetspecialdescription
		end
		if inst.SetSkeletonDescription and not inst.OldSetSkeletonDescription then
			inst.OldSetSkeletonDescription=inst.SetSkeletonDescription
			function NewSetSkeletonDescription(inst, ...)
				inst.OldSetSkeletonDescription(inst, ...)
				reassignfn(inst)
			end
			inst.SetSkeletonDescription=NewSetSkeletonDescription
		end
		if inst.OnLoad and not inst.OldOnLoad then
			inst.OldOnLoad=inst.OnLoad
			function NewOnLoad(inst, ...)
				inst.OldOnLoad(inst, ...)
				reassignfn(inst)				
			end
			inst.OnLoad=NewOnLoad
		end
	end)
end











