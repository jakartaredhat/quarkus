package container;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VersionTest {
    public static void main(String[] args) {
        VersionInformation v = new VersionInformation("4.1.1-0.lite");
        System.out.printf("major:%d\nminor:%d\npatch:%d\nbuild:%d\nqualifier:%s\n", v.getMajor(), v.getMinor(), v.getPatch(),
                          v.getBuildNumber(), v.getQualifier());
        v = new VersionInformation("4.1.1-0-lite");
        System.out.printf("major:%d\nminor:%d\npatch:%d\nbuild:%d\nqualifier:%s\n", v.getMajor(), v.getMinor(), v.getPatch(),
                          v.getBuildNumber(), v.getQualifier());
        v = new VersionInformation("4.1.1-20210621-lite");
        System.out.printf("major:%d\nminor:%d\npatch:%d\nbuild:%d\nqualifier:%s\n", v.getMajor(), v.getMinor(), v.getPatch(),
                          v.getBuildNumber(), v.getQualifier());
    }

}


/**
 * This class will parse the version based on the given pattern.
 *
 * @author Karl Heinz Marbaise <a href="mailto:khmarbaise@apache.org">khmarbaise@apache.org</a>
 *
 */
class VersionInformation
{
    private static final String MAJOR_MINOR_PATCH_PATTERN = "^((\\d+)(\\.(\\d+)(\\.(\\d+))?)?)";

    private static final Pattern MAJOR_MINOR_PATCH = Pattern.compile( MAJOR_MINOR_PATCH_PATTERN );

    private static final Pattern DIGITS = Pattern.compile( MAJOR_MINOR_PATCH_PATTERN + "(.*)$" );

    private static final Pattern BUILD_NUMBER = Pattern.compile( "(((\\-)(\\d+)(.*))?)|(\\.(.*))|(\\-(.*))|(.*)$" );

    private int major;

    private int minor;

    private int patch;

    private long buildNumber;

    private String qualifier;

    private void parseBuildNumber( String buildNumberPart )
    {
        Matcher matcher = BUILD_NUMBER.matcher( buildNumberPart );
        if ( matcher.matches() )
        {
            String buildNumber = matcher.group( 4 );
            String qualifier = matcher.group( 5 );

            if ( buildNumber != null )
            {
                setBuildNumber( Long.parseLong( buildNumber ) );
            }

            if ( matcher.group( 7 ) != null )
            {
                qualifier = matcher.group( 7 );
            }
            // Starting with "-"
            if ( matcher.group( 9 ) != null )
            {
                qualifier = matcher.group( 9 );
            }
            if ( qualifier != null )
            {
                if ( qualifier.trim().length() == 0 )
                {
                    setQualifier( null );
                }
                else
                {
                    setQualifier( qualifier );
                }
            }
            else
            {
                setQualifier( null );
            }
        }
    }

    private void parseMajorMinorPatchVersion( String version )
    {
        Matcher matcher = MAJOR_MINOR_PATCH.matcher( version );
        if ( matcher.matches() )
        {
            String majorString = matcher.group( 2 );
            String minorString = matcher.group( 4 );
            String patchString = matcher.group( 6 );

            if ( majorString != null )
            {
                setMajor( Integer.parseInt( majorString ) );
            }
            if ( minorString != null )
            {
                setMinor( Integer.parseInt( minorString ) );
            }
            if ( patchString != null )
            {
                setPatch( Integer.parseInt( patchString ) );
            }
        }

    }

    public VersionInformation( String version )
    {
        Matcher matcherDigits = DIGITS.matcher( version );
        if ( matcherDigits.matches() )
        {
            parseMajorMinorPatchVersion( matcherDigits.group( 1 ) );
            parseBuildNumber( matcherDigits.group( 7 ) );
        }
        else
        {
            setQualifier( version );
        }
    }

    public int getMajor()
    {
        return major;
    }

    public void setMajor( int major )
    {
        this.major = major;
    }

    public int getMinor()
    {
        return minor;
    }

    public void setMinor( int minor )
    {
        this.minor = minor;
    }

    public int getPatch()
    {
        return patch;
    }

    public void setPatch( int patch )
    {
        this.patch = patch;
    }

    public long getBuildNumber()
    {
        return buildNumber;
    }

    public void setBuildNumber( long buildNumber )
    {
        this.buildNumber = buildNumber;
    }

    public String getQualifier()
    {
        return qualifier;
    }

    public void setQualifier( String qualifier )
    {
        this.qualifier = qualifier;
    }

}