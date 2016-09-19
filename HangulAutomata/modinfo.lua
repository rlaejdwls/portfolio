name = "I Love Korean"
forumthread = ""
description = "한/영 전환키를 누르고 채팅을 해야하는 불편함을 해소하기 위한 모드"
author = "도닦는소년"
version = "1.0.1"
api_version = 10
dont_starve_compatible = false
reign_of_giants_compatible = false
dst_compatible = true
all_clients_require_mod = false
client_only_mod = true

icon_atlas = "modicon.xml"
icon = "modicon.tex"

configuration_options =
{
	{
		name = "default_language",
		label = "기본 언어",
		options =	{
						{description = "한글", data = "&kr"},
						{description = "영어", data = "&en"},
					},

		default = "&kr",
	}
}