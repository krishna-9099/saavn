# JioSaavn API Endpoints

This document lists every API endpoint used in the `saavn_play` project along with the full request URL and an example `curl` invocation.

## Base URL Structure
```
https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6
```
- Default API version: **v6** (`api_version=6`)
- Legacy calls still use **v4** (`api_version=4`) for compatibility with older endpoints

---

## 1. Search APIs

### Autocomplete Search (`autocomplete.get`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=autocomplete.get&query=arijit singh"
```

### Search Songs (`search.getResults`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=search.getResults&q=faded&p=0&n=10"
```

### Search Albums (`search.getAlbumResults`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=search.getAlbumResults&q=rockstar&p=0&n=10"
```

### Search Artists (`search.getArtistResults`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=search.getArtistResults&q=shreya&p=0&n=10"
```

### Search Playlists (`search.getPlaylistResults`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=search.getPlaylistResults&q=party&p=0&n=10"
```

---

## 2. Content Details

### Song Details (`song.getDetails`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=song.getDetails&pids=3uA_1bIu"
```

### Album Details (`content.getAlbumDetails`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=content.getAlbumDetails&albumid=123456"
```

### Playlist Details (`playlist.getDetails`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=playlist.getDetails&listid=98765432"
```

### Featured Playlists (`content.getFeaturedPlaylists`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=content.getFeaturedPlaylists&n=30&p=1&language=hindi"
```

---

## 3. Artist Endpoints

### Artist Details (`artist.getArtistPageDetails`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=artist.getArtistPageDetails&artistId=459320"
```

### Artist Songs (`artist.getArtistMoreSong`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=artist.getArtistMoreSong&artistId=459320&page=0"
```

### Artist Albums (`artist.getArtistMoreAlbum`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=artist.getArtistMoreAlbum&artistId=459320&page=0"
```

---

## 4. Lyrics

### Get Lyrics (`lyrics.getLyrics`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=lyrics.getLyrics&lyrics_id=3uA_1bIu"
```

---

## 5. Radio

### Browse Radio Modules (`content.getBrowseModules`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=6&__call=content.getBrowseModules"
```

### Create Radio Station (`webradio.createFeaturedStation`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=webradio.createFeaturedStation&name=Chill&language=english"
```

### Get Radio Songs (`webradio.getSong`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=webradio.getSong&stationid=featured_chill&k=5&next=1"
```

---

## 6. Home & Podcasts

### Launch Data (`webapi.getLaunchData`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=webapi.getLaunchData"
```

### Top Podcast Shows (`content.getTopShows`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=content.getTopShows&n=20&p=1"
```

---

## 7. Generic Web APIs

### Generic Token Lookup (`webapi.get`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=webapi.get&token=HvA1Hqgh83E_&type=album&includeMetaTags=0"
```

### Browse Hover Menu (`webapi.getBrowseHoverDetails`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=webapi.getBrowseHoverDetails&language=english&is_entity_page=true"
```

### Footer Details (`webapi.getFooterDetails`)
```bash
curl "https://www.jiosaavn.com/api.php?_format=json&_marker=0&ctx=web6dot0&api_version=4&__call=webapi.getFooterDetails&language=english"
```


Request URL
https://www.jiosaavn.com/api.php?__call=content.getListeningHistory&page=&size=40&api_version=4&ctx=web6dot0&_format=json&_marker=0
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin

Request URL
https://www.jiosaavn.com/api.php?__call=webapi.getConfigDetailsCSR&api_version=4&_format=json&_marker=0&ctx=web6dot0
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin

{
    "user_state": {
        "user_logged_in": 1,
        "current_pack_details": {
            "title": "Pro Individual",
            "subtitle": "",
            "highlight": "Your plan renews on 21\/05\/2026",
            "progress_cycle": {
                "v": true,
                "start_ts": 1771597455,
                "end_ts": 1779369855
            },
            "cta": {
                "text": "Play Now",
                "deeplink": "jiosaavn:\/\/open\/homepagescroll\/",
                "deeplink_google": "jiosaavn:\/\/open\/homepagescroll\/",
                "url": "\/"
            },
            "heading": "Your Current Pack",
            "description": [
                {
                    "feature_text": "Ad-free\nMusic",
                    "feature_included": true
                },
                {
                    "feature_text": "Unlimited\nJioTunes",
                    "feature_included": true
                },
                {
                    "feature_text": "Unlimited\nDownloads",
                    "feature_included": true
                },
                {
                    "feature_text": "2X Better\nSound Quality",
                    "feature_included": true
                }
            ]
        },
        "status_text": "Pro",
        "username": "+XXXXXXXX1722",
        "custom_username": "jiosaavn_722_o6ympd",
        "initials": "JS",
        "dob": "0000-00-00",
        "age": "",
        "gender": "m",
        "uid": "c6c140696f508ad038a3d56ee27e487a",
        "pass_reset_avail": false,
        "encrypted_uid": "RHI5B7vWmmasf1pPnS2H1giORt5-e9qVEa4rMtC24c8_",
        "enc_uid": "xLG8k.JRnjCUzZU8OjoN0hIrzqMd0-Q7D45QMYEumMc_",
        "firstname": "",
        "lastname": "",
        "network": "phone",
        "fbid": "",
        "email": "",
        "phone_number": "+919540691722",
        "registered_phone": "+XXXXXXXX1722",
        "is_jio_user": true,
        "registered_date": "2024-07-18 04:01:32",
        "prostatus": {
            "type": "pro",
            "offer_trial": "no",
            "product": "prime",
            "expiration_timestamp": 1779369855,
            "slots_used": [
                {
                    "id": "SsiaMwrQYVoQzKO3IyIlR1yJnienvB1gnXph5GTxFn8=",
                    "name": "SM-X115"
                }
            ],
            "pro_features": [
                "jtune",
                "unlimited_skip",
                "full_pro",
                "download",
                "auto_play",
                "streaming",
                "noads",
                "pro_only_video",
                "video_playback",
                "add_video"
            ],
            "tier_id": "fullpro_tier",
            "title": "Pro Individual",
            "subtitle": "Ad-free Music, Unlimited JioTunes, Unlimited Downloads, 2X Better Sound Quality",
            "auto_renewal": "1",
            "product_details": {
                "name": "prime",
                "cat": "subscription",
                "id": "juspay_sub_1month",
                "dormant_period": "365",
                "grace_period": "7",
                "storage_limit": "9999.00",
                "device_limit": "5",
                "card_length": "2",
                "period": "1",
                "period_unit": "MONTH",
                "description": "1 Month Of Validity",
                "discount": "0",
                "tier_id": "fullpro_tier",
                "sku_name": "fullpro_tier_1M.SUBS",
                "cross_vendor_map": {
                    "google": "prime"
                },
                "currency": "\u20b9",
                "currency_sym": "\u20b9",
                "amount": "99.00",
                "card_price": "99",
                "price_display": "\u20b9 99",
                "price_display_per_unit": "99\/month",
                "highlight": "Renews every 1 Month",
                "strikethrough_price": "",
                "card_duration": " \/ month",
                "duration": "per month",
                "price": "99",
                "price_text": "",
                "vendor": [
                    "google",
                    "apple"
                ],
                "autorenew_text": "renews every 1 month",
                "success_page": {
                    "page_desc": "Congratulations on your purchase! We hope you enjoy the JioSaavn premium features available with your pack. ",
                    "page_subtitle": "1 Month Subscription",
                    "action_button": "Got it",
                    "page_header": "Welcome to JioSaavn Pro Individual!",
                    "gradient": {
                        "start": "#064280",
                        "end": "#00DDBB",
                        "middle": "#0690D9"
                    },
                    "icon_tint": "#066CB0"
                }
            },
            "vendor": "juspay",
            "total_days": 30
        },
        "image": "",
        "opted_for_deletion": false,
        "account_delete_date": "3 Apr 2026",
        "to_be_deleted_text": "Your account will be deleted after 30 days. If you change your mind, you can sign in to JioSaavn during these 30 days and choose to keep your account.",
        "login_country_code": "",
        "login_phone_number": "",
        "login_method": "phone",
        "login_with_string": "+XXXXXXXX1722"
    },
    "limiting_features": {
        "streaming_limit": {
            "time_limit": 180
        }
    },
    "social": {
        "image_bg_color": "#FF8B4D",
        "image_text_color": "#FFFFFF"
    },
    "weeklyTop15": {
        "hindi": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/dumdaar-hits\/8MT-LQlP35c_",
            "id": "49"
        },
        "tamil": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/mersal-hits\/x7NaWNE3kRw_",
            "id": "2574965"
        },
        "telugu": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/adhire-hits-\/C3TvSMCoP2A_",
            "id": "2574962"
        },
        "marathi": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/jhakaas-hits\/ZO-X4cZZm64_",
            "id": "1679071"
        },
        "gujarati": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/dhamakedar-hits\/lD9c76kSeqc_",
            "id": "2573977"
        },
        "english": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/future-hits\/LdbVc1Z5i9E_",
            "id": "7386899"
        },
        "bengali": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/fatafati-hits\/zDijQvMQA4w_",
            "id": "2676586"
        },
        "kannada": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/shaane-hits\/FnWfzTurhhg_",
            "id": "2676953"
        },
        "bhojpuri": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/jabariya-hits\/,RCpjh,hgVE_",
            "id": "2677097"
        },
        "punjabi": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/att-hits\/W6DUe-fP3X8_",
            "id": "2676373"
        },
        "malayalam": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/kidu-hits\/th5xS,pbZQ0_",
            "id": "3344648"
        },
        "urdu": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/kamaal-hits\/Vzehd0ZQty4_",
            "id": "79491317"
        },
        "rajasthani": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/weekly-top-songs\/Xm5PW-mxs4c_",
            "id": "79487390"
        },
        "odia": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/bobal-hits-\/uEMtMB9bLAI_",
            "id": "85935274"
        },
        "assamese": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/dhuniya-hits\/WJy2xBPol-4wkg5tVhI3fw__",
            "id": "107740743"
        },
        "haryanvi": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/bawaal-hits\/ar5lExlDmbwwkg5tVhI3fw__",
            "id": "157145953"
        },
        "sanskrit": {
            "perma_url": "https:\/\/www.jiosaavn.com\/featured\/best-of-sanskrit\/66b-Iv21,7iTb7czG7lKZg__",
            "id": "1265949697"
        }
    }
}

Request URL
https://www.jiosaavn.com/api.php?__call=library.getAll&api_version=4&_format=json&_marker=0&ctx=web6dot0
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin

{"song":["0C4nMnXH"],"playlist":[{"id":"1280688027","ts":"0"}],"show":[],"artist":["14327531","712878","455120","455130","459320","464932","456323","473591"],"user":{"fbid":"","firstname":"","lastname":"","uid":"c6c140696f508ad038a3d56ee27e487a","username":"+XXXXXXXX1722","follower_count":"0","following_count":"8","image":"","initials":"JS"}}

Request URL
https://www.jiosaavn.com/api.php?__call=library.getDetails&entity_ids=0C4nMnXH&entity_type=song&api_version=4&_format=json&_marker=0&ctx=web6dot0&n=50
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin
{"songs":[{"id":"0C4nMnXH","title":"Yaari","subtitle":"Masoom Sharma, Rohit Lamba - Yaari","header_desc":"","type":"song","perma_url":"https:\/\/www.jiosaavn.com\/song\/yaari\/QCtfXzleb3s","image":"https:\/\/c.saavncdn.com\/504\/Yaari-Hindi-2025-20250731193932-150x150.jpg","language":"haryanvi","year":"2025","play_count":"6235083","explicit_content":"0","list_count":"0","list_type":"","list":"","more_info":{"music":"Masoom Sharma, Rohit Lamba","album_id":"66814487","album":"Yaari","label":"Kiara Records","label_id":"2131643","origin":"none","is_dolby_content":false,"320kbps":"true","encrypted_media_url":"ID2ieOjCrwfgWvL5sXl4B1ImC5QfbsDyHARNLW\/k4RskEaNxG\/lmz5+hgIxJjg0KtlHxg69q0CAmFNeNPHAmCBw7tS9a8Gtq","encrypted_cache_url":"","encrypted_drm_cache_url":"","encrypted_drm_media_url":"ID2ieOjCrwdjlkMElYlzWCptgNdUpWD81Dckzhd5BAeIlDl4LS3JvDjlKauNJyQYnBvgB45oxZvtYMgOmDDGC492mytrdt3FDnQW0nglPS4=","album_url":"https:\/\/www.jiosaavn.com\/album\/yaari\/DjARC-Fh26Y_","duration":"170","rights":{"code":"0","cacheable":"true","delete_cached_object":"false","reason":""},"cache_state":"false","has_lyrics":"false","lyrics_snippet":"","starred":"false","copyright_text":"\u2117 2025 Kiara Records","artistMap":{"primary_artists":[{"id":"497099","name":"Masoom Sharma","role":"primary_artists","image":"https:\/\/c.saavncdn.com\/artists\/Masoom_Sharma_003_20250619064935_150x150.jpg","type":"artist","perma_url":"https:\/\/www.jiosaavn.com\/artist\/masoom-sharma-songs\/zWdhMOxbrU8_"},{"id":"9934553","name":"Rohit Lamba","role":"primary_artists","image":"","type":"artist","perma_url":"https:\/\/www.jiosaavn.com\/artist\/rohit-lamba-songs\/GNJLEdEwNB8_"}],"featured_artists":[],"artists":[{"id":"497099","name":"Masoom Sharma","role":"music","image":"https:\/\/c.saavncdn.com\/artists\/Masoom_Sharma_003_20250619064935_150x150.jpg","type":"artist","perma_url":"https:\/\/www.jiosaavn.com\/artist\/masoom-sharma-songs\/zWdhMOxbrU8_"},{"id":"9934553","name":"Rohit Lamba","role":"music","image":"","type":"artist","perma_url":"https:\/\/www.jiosaavn.com\/artist\/rohit-lamba-songs\/GNJLEdEwNB8_"},{"id":"678936","name":"Amarjeet Singh","role":"lyricist","image":"https:\/\/c.saavncdn.com\/068\/Maa-De-Jai-Kare-Hindi-2018-20180313-150x150.jpg","type":"artist","perma_url":"https:\/\/www.jiosaavn.com\/artist\/amarjeet-singh-songs\/4REfKtZ3KPA_"}]},"release_date":"2025-08-12","label_url":"\/label\/kiara-records-albums\/BULElqWS1ik_","vcode":"010912023354552","vlink":"https:\/\/jiotunepreview.jio.com\/content\/Converted\/010912023311835.mp3","triller_available":false,"request_jiotune_flag":false,"webp":"true"},"button_tooltip_info":[],"pro_hva_campaigns":[]}]}

Request URL
https://www.jiosaavn.com/api.php?__call=library.getDetails&entity_ids=1280688027&entity_type=playlist&api_version=4&_format=json&_marker=0&ctx=web6dot0&n=50
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin
[{"id":"1280688027","title":"Best of 2025","subtitle":"20 Songs","header_desc":"","type":"playlist","perma_url":"https:\/\/www.jiosaavn.com\/s\/playlist\/c6c140696f508ad038a3d56ee27e487a\/best-of-2025\/Uim32Tqrp9K5MW-661Z5Dg__","image":"https:\/\/pli.saavncdn.com\/80\/27\/1280688027.jpg?bch=-62169953822","language":"","year":"","play_count":"","explicit_content":"0","list_count":"0","list_type":"","list":[],"more_info":{"listid":"1280688027","username":"","uid":"c6c140696f508ad038a3d56ee27e487a","listname":"Best of 2025","contents":"_xepYjRk,16MIvBRf,5_1qET9W,QPBc7yAL,P8BoRwBV,jye2JQaL,9Ysvyb_3,hEiNZpkR,4UMppKZt,2hnSEjMO,rXP6SuPQ,Q6l0a09y,DOYdLczc,ZsGGaRro,T2CRU6B2,wtRnvYVS,GHXYZHMU,wtvPJMD4,sVFpQfML,2w14bOXI","creation_date":"2025-12-13 03:57:30","share":"1","last_updated":-62169955622,"favourite":"0","owner":"","owner_listid":"","normalized_listname":"","follower_count":"0","extra_info":"","song_count":"20","firstname":"","lastname":"","is_followed":"false","isFY":false,"video_count":"3"},"button_tooltip_info":[],"pro_hva_campaigns":[]}]

Request URL
https://www.jiosaavn.com/api.php?__call=library.getDetails&entity_ids=14327531,712878,455120,455130,459320,464932,456323,473591&entity_type=artist&api_version=4&_format=json&_marker=0&ctx=web6dot0&n=50
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin

[{"id":"14327531","title":"King","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/King_004_20240819092014_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/king-songs\/axyoun05Pkg_"},{"id":"455120","title":"Alka Yagnik","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Alka_Yagnik_002_20220314192930_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/alka-yagnik-songs\/uqRkqsl4ZnQ_"},{"id":"455130","title":"Shreya Ghoshal","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Shreya_Ghoshal_007_20241101074144_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/shreya-ghoshal-songs\/lIHlwHaxTZ0_"},{"id":"456323","title":"Pritam","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Pritam_Chakraborty-20170711073326_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/pritam-songs\/OaFg9HPZgq8_"},{"id":"459320","title":"Arijit Singh","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Arijit_Singh_004_20241118063717_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/arijit-singh-songs\/LlRWpHzy3Hk_"},{"id":"464932","title":"Neha Kakkar","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Neha_Kakkar_007_20241212115832_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/neha-kakkar-songs\/EkEBV7JAU-I_"},{"id":"473591","title":"Khesari Lal Yadav","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Khesari_Lal_Yadav_004_20241119074533_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/khesari-lal-yadav-songs\/CJzjhAiKs7c_"},{"id":"712878","title":"Guru Randhawa","type":"artist","image":"https:\/\/c.saavncdn.com\/artists\/Guru_Randhawa_004_20250701125845_150x150.jpg","perma_url":"https:\/\/www.jiosaavn.com\/artist\/guru-randhawa-songs\/zMPBu01k9ZI_"}]

Request URL
https://www.jiosaavn.com/api.php?__call=social.getFollowingDetails&type=artist&api_version=4&_format=json&_marker=0&ctx=web6dot0
Request Method
GET
Status Code
200 OK
Remote Address
[2600:140f:1800::173a:5f90]:443
Referrer Policy
strict-origin-when-cross-origin
{"follow":[{"type":"artist","details":{"artistid":"712878","name":"Guru Randhawa","image":"https:\/\/c.saavncdn.com\/artists\/Guru_Randhawa_004_20250701125845_150x150.jpg","follower_count":12965896,"is_followed":true}},{"type":"artist","details":{"artistid":"473591","name":"Khesari Lal Yadav","image":"https:\/\/c.saavncdn.com\/artists\/Khesari_Lal_Yadav_004_20241119074533_150x150.jpg","follower_count":7315508,"is_followed":true}},{"type":"artist","details":{"artistid":"464932","name":"Neha Kakkar","image":"https:\/\/c.saavncdn.com\/artists\/Neha_Kakkar_007_20241212115832_150x150.jpg","follower_count":21452556,"is_followed":true}},{"type":"artist","details":{"artistid":"459320","name":"Arijit Singh","image":"https:\/\/c.saavncdn.com\/artists\/Arijit_Singh_004_20241118063717_150x150.jpg","follower_count":97020994,"is_followed":true}},{"type":"artist","details":{"artistid":"456323","name":"Pritam","image":"https:\/\/c.saavncdn.com\/artists\/Pritam_Chakraborty-20170711073326_150x150.jpg","follower_count":31630200,"is_followed":true}},{"type":"artist","details":{"artistid":"455130","name":"Shreya Ghoshal","image":"https:\/\/c.saavncdn.com\/artists\/Shreya_Ghoshal_007_20241101074144_150x150.jpg","follower_count":39233894,"is_followed":true}},{"type":"artist","details":{"artistid":"455120","name":"Alka Yagnik","image":"https:\/\/c.saavncdn.com\/artists\/Alka_Yagnik_002_20220314192930_150x150.jpg","follower_count":56340783,"is_followed":true}},{"type":"artist","details":{"artistid":"14327531","name":"King","image":"https:\/\/c.saavncdn.com\/artists\/King_004_20240819092014_150x150.jpg","follower_count":40725556,"is_followed":true}}],"counts":{"following":{"usersCount":0,"artistsCount":8,"playlistsCount":0},"followed_by":{"usersCount":0},"status":"success"},"status":"success"}


