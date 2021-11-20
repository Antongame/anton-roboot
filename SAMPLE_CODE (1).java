/**
 * Copyright (c) 2015-present, Facebook, Inc. All rights reserved.
 *
 * You are hereby granted a non-exclusive, worldwide, royalty-free license to
 * use, copy, modify, and distribute this software in source code or binary
 * form for use in connection with the web services and APIs provided by
 * Facebook.
 *
 * As with any software that integrates with the Facebook platform, your use
 * of this software is subject to the Facebook Developer Principles and
 * Policies [http://developers.facebook.com/policy/]. This copyright notice
 * shall be included in all copies or substantial portions of the software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

import com.facebook.ads.sdk.*;
import java.io.File;
import java.util.Arrays;

public class SAMPLE_CODE {
  public static void main (String args[]) throws APIException {

    String access_token = "EAAhQbrMpElIBAEUl36sFbmZC0ISHumwDOPK7LIkks6zlvVlHwcK6wViQ8s6AhYH2qNSG1I59oHY3SeJydK2pNZBtZBnxV5RA8GTSOMv1GjNQzNwrPF1SCtx3ZCZB7X7iL7SMAow1pY9gt95bs8eFnB1XlQrvm8R6TfVYuTEDBmG707hHZCMByRvOiYKKAZBTbsZD";
    String app_secret = "031f583cb9c0d9104968ec8f73346786";
    String ad_account_id = "267330185239272";
    String audience_name = "Anton viewrs data streaming";
    String audience_retention_days = "30";
    String pixel_id = "342826574277275";
    String app_id = "2340236196123218";
    APIContext context = new APIContext(access_token).enableDebug(true);

    Campaign campaign = new AdAccount(ad_account_id, context).createCampaign()
      .setName("My Campaign")
      .setBuyingType("AUCTION")
      .setObjective(Campaign.EnumObjective.VALUE_LINK_CLICKS)
      .setStatus(Campaign.EnumStatus.VALUE_PAUSED)
      .execute();
    String campaign_id = campaign.getId();
    CustomAudience customAudience = new AdAccount(ad_account_id, context).createCustomAudience()
      .setPixelId(pixel_id)
      .setName(audience_name)
      .setSubtype(CustomAudience.EnumSubtype.VALUE_WEBSITE)
      .setRetentionDays(audience_retention_days)
      .setRule("{\"url\":{\"i_contains\":\"\"}}")
      .setPrefill(true)
      .execute();
    String custom_audience_id = customAudience.getId();
    AdSet adSet = new AdAccount(ad_account_id, context).createAdSet()
      .setName("My AdSet")
      .setOptimizationGoal(AdSet.EnumOptimizationGoal.VALUE_REACH)
      .setBillingEvent(AdSet.EnumBillingEvent.VALUE_IMPRESSIONS)
      .setBidAmount(20L)
      .setDailyBudget(1000L)
      .setCampaignId(campaign_id)
      .setTargeting(
          new Targeting()
            .setFieldCustomAudiences(Arrays.asList(
              new RawCustomAudience()
                .setFieldId(custom_audience_id)
            ))
            .setFieldGeoLocations(
              new TargetingGeoLocation()
                .setFieldCountries(Arrays.asList("US"))
            )
        )
      .setStatus(AdSet.EnumStatus.VALUE_PAUSED)
      .execute();
    String ad_set_id = adSet.getId();
    AdCreative creative = new AdAccount(ad_account_id, context).createAdCreative()
      .setName("My Creative")
      .setTitle("My Page Like Ad")
      .setBody("Like My Page")
      .setObjectUrl("www.facebook.com")
      .setLinkUrl("www.facebook.com")
      .setImageUrl("http://www.facebookmarketingdevelopers.com/static/images/resource_1.jpg")
      .execute();
    String creative_id = creative.getId();
    Ad ad = new AdAccount(ad_account_id, context).createAd()
      .setName("My Ad")
      .setAdsetId(ad_set_id)
      .setCreative(
          new AdCreative()
            .setFieldId(creative_id)
        )
      .setStatus(Ad.EnumStatus.VALUE_PAUSED)
      .execute();
    String ad_id = ad.getId();
    new Ad(ad_id, context).getPreviews()
      .setAdFormat(AdPreview.EnumAdFormat.VALUE_DESKTOP_FEED_STANDARD)
      .execute();
  }
}
